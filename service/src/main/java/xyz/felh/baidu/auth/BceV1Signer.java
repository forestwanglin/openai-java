package xyz.felh.baidu.auth;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import xyz.felh.utils.Preconditions;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

import static lombok.Lombok.checkNotNull;
import static xyz.felh.baidu.constant.BaiduAiConstants.*;

@Slf4j
public class BceV1Signer {

    private static final BitSet URI_UNRESERVED_CHARACTERS = new BitSet();
    private static final String[] PERCENT_ENCODED_STRINGS = new String[256];

    private static final String BCE_AUTH_VERSION = "bce-auth-v1";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Charset UTF8 = Charset.forName(DEFAULT_ENCODING);
    private static final int expirationInSeconds = 1800;
    private static final Set<String> defaultHeadersToSign = new HashSet<>();

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        for (int i = '0'; i <= '9'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        URI_UNRESERVED_CHARACTERS.set('-');
        URI_UNRESERVED_CHARACTERS.set('.');
        URI_UNRESERVED_CHARACTERS.set('_');
        URI_UNRESERVED_CHARACTERS.set('~');

        for (int i = 0; i < PERCENT_ENCODED_STRINGS.length; ++i) {
            PERCENT_ENCODED_STRINGS[i] = String.format("%%%02X", i);
        }

        defaultHeadersToSign.add(HEADER_HOST.toLowerCase());
        defaultHeadersToSign.add(HEADER_CONTENT_LENGTH.toLowerCase());
        defaultHeadersToSign.add(HEADER_CONTENT_TYPE.toLowerCase());
        defaultHeadersToSign.add(HEADER_CONTENT_MD5.toLowerCase());
    }


    public String sign(BceRequest request, BceCredentials credentials) {
        String accessKey = credentials.getAccessKey();
        String secretKey = credentials.getSecretKey();
        // 添加header host
        request.getHeaders().put(HEADER_HOST, generateHostHeader(request.getUri()));
        String iso8601Date = request.getHeaders().get(HEADER_BCE_DATE);

        String authString = BCE_AUTH_VERSION + "/" + accessKey + "/" + iso8601Date + "/" + expirationInSeconds;
        String signingKey = sha256Hex(secretKey, authString);

        String canonicalURI = getCanonicalURIPath(request.getUri().getPath());
        String canonicalQueryString = getCanonicalQueryString(getParameters(request.getUri()), true);

        Set<String> headersToSignSet = Set.of(HEADER_BCE_DATE);
        SortedMap<String, String> headersToSign = getHeadersToSign(request.getHeaders(), headersToSignSet);
        String canonicalHeader = getCanonicalHeaders(headersToSign);
        String signedHeaders = String.join(";", headersToSign.keySet());
        signedHeaders = signedHeaders.trim().toLowerCase();

        String canonicalRequest =
                request.getHttpMethod().toUpperCase() + "\n" + canonicalURI + "\n" + canonicalQueryString + "\n" + canonicalHeader;

        String signature = sha256Hex(signingKey, canonicalRequest);
        String authorizationHeader = authString + "/" + signedHeaders + "/" + signature;

//        log.info("host: {}", request.getUri().getHost());
//        log.info("iso8601Date: {}", iso8601Date);
//        log.info("canonicalRequest: \n{}", canonicalRequest);
//        log.info("canonicalURI: {}", canonicalURI);
//        log.info("canonicalQueryString: {}", canonicalQueryString);
//        log.info("signingKey: {}", signingKey);
//        log.info("signature: {}", signature);
//        log.info("authString: {}", authString);
//        log.info("authorizationHeader: {}", authorizationHeader);
        return authorizationHeader;
    }

    private String generateHostHeader(URI uri) {
        String host = uri.getHost();
        if (isUsingNonDefaultPort(uri)) {
            host += ":" + uri.getPort();
        }
        return host;
    }

    private boolean isUsingNonDefaultPort(URI uri) {
        String scheme = uri.getScheme().toLowerCase();
        int port = uri.getPort();
        if (port <= 0) {
            return false;
        }
        if (scheme.equals("http")) {
            return port != 80;
        }
        if (scheme.equals("https")) {
            return port != 443;
        }
        return false;
    }

    private String getCanonicalHeaders(SortedMap<String, String> headers) {
        if (headers.isEmpty()) {
            return "";
        }
        List<String> headerStrings = new ArrayList<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (key == null) {
                continue;
            }
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            headerStrings.add(normalize(key.trim().toLowerCase()) + ':' + normalize(value.trim()));
        }
        Collections.sort(headerStrings);

        return String.join("\n", headerStrings);
    }

    private SortedMap<String, String> getHeadersToSign(Map<String, String> headers, Set<String> headersToSign) {
        SortedMap<String, String> ret = new TreeMap<>();
        if (headersToSign != null) {
            // 改成小写
            Set<String> tempSet = new HashSet<>();
            for (String header : headersToSign) {
                tempSet.add(header.trim().toLowerCase());
            }
            headersToSign = tempSet;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                if (isDefaultHeaderToSign(key) || (headersToSign != null && headersToSign.contains(key.toLowerCase())
                        && !HEADER_AUTHORIZATION.equalsIgnoreCase(key))) {
                    ret.put(key, entry.getValue());
                }
            }
        }
        return ret;
    }

    private boolean isDefaultHeaderToSign(String header) {
        header = header.trim().toLowerCase();
        return header.startsWith(HEADER_BCE_PREFIX) || defaultHeadersToSign.contains(header);
    }

    private Map<String, String> getParameters(URI uri) {
        Map<String, String> parameters = new HashMap<>();
        String query = uri.getQuery();
        if (Preconditions.isNotBlank(query)) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] param = pair.split("=");
                if (param.length == 2) {
                    parameters.put(param[0], param[1]);
                } else {
                    parameters.put(param[0], null);
                }
            }
        }
        return parameters;
    }

    private String getCanonicalQueryString(Map<String, String> parameters, boolean forSignature) {
        if (parameters.isEmpty()) {
            return "";
        }
        List<String> parameterStrings = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (forSignature && HEADER_AUTHORIZATION.equalsIgnoreCase(entry.getKey())) {
                continue;
            }
            String key = entry.getKey();
            checkNotNull(key, "parameter key should not be null");
            String value = entry.getValue();
            if (value == null) {
                if (forSignature) {
                    parameterStrings.add(normalize(key) + '=');
                } else {
                    parameterStrings.add(normalize(key));
                }
            } else {
                parameterStrings.add(normalize(key) + '=' + normalize(value));
            }
        }
        Collections.sort(parameterStrings);

        return String.join("&", parameterStrings);
    }


    private String getCanonicalURIPath(String path) {
        if (path == null) {
            return "/";
        } else if (path.startsWith("/")) {
            return normalizePath(path);
        } else {
            return "/" + normalizePath(path);
        }
    }

    public String normalizePath(String path) {
        return normalize(path).replace("%2F", "/");
    }

    public String normalize(String value) {
        try {
            StringBuilder builder = new StringBuilder();
            for (byte b : value.getBytes(DEFAULT_ENCODING)) {
                if (URI_UNRESERVED_CHARACTERS.get(b & 0xFF)) {
                    builder.append((char) b);
                } else {
                    builder.append(PERCENT_ENCODED_STRINGS[b & 0xFF]);
                }
            }
            return builder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String sha256Hex(String signingKey, String stringToSign) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(signingKey.getBytes(UTF8), "HmacSHA256"));
            return bytesToHex(mac.doFinal(stringToSign.getBytes(UTF8)));
        } catch (Exception e) {
            return null;
        }
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    @Data
    @Builder
    public static class BceRequest {
        @Builder.Default
        private Map<String, String> headers = new HashMap<>();
        private URI uri;
        private String httpMethod;
    }

    @Data
    @Builder
    public static class BceCredentials {
        private String accessKey;
        private String secretKey;
    }

}
