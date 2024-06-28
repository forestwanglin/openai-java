package xyz.felh.baidu.interceptor;

import lombok.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import xyz.felh.baidu.auth.BceV1Signer;
import xyz.felh.utils.DateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import static xyz.felh.baidu.constant.BaiduAiConstants.HEADER_AUTHORIZATION;
import static xyz.felh.baidu.constant.BaiduAiConstants.HEADER_BCE_DATE;

public class AuthenticationInterceptor implements Interceptor {

    private final String ak;
    private final String sk;

    public AuthenticationInterceptor(String ak, String sk) {
        this.ak = ak;
        this.sk = sk;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        String iso8601Date = DateUtils.formatISO8601(new Date());
        Request request = chain.request();
        BceV1Signer signer = new BceV1Signer();
        String auth = signer.sign(BceV1Signer.BceRequest.builder()
                        .uri(request.url().uri())
                        .httpMethod(request.method())
                        .headers(new HashMap<>() {{
                            put(HEADER_BCE_DATE, iso8601Date);
                        }})
                        .build(),
                BceV1Signer.BceCredentials.builder()
                        .accessKey(ak)
                        .secretKey(sk)
                        .build());
        Request.Builder requestBuilder = chain.request().newBuilder()
                .header(HEADER_BCE_DATE, iso8601Date)
                .header(HEADER_AUTHORIZATION, auth);
        return chain.proceed(requestBuilder.build());
    }

}
