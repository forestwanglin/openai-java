package xyz.felh.openai;

import lombok.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * OkHttp Interceptor that adds an authorization token header
 */
public class AuthenticationInterceptor implements Interceptor {

    private final String token;
    private final String orgId;

    /**
     * Constructor
     *
     * @param token OPENAI_API_KEY
     * @param orgId OPENAI ORGANIZATION ID
     */
    public AuthenticationInterceptor(String token, String orgId) {
        this.token = token;
        this.orgId = orgId;
    }

    @Override
    public @NonNull Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request()
                .newBuilder()
                .header("Authorization", "Bearer " + token);
        if (orgId != null) {
            requestBuilder.header("OpenAI-Organization", orgId);
        }
        return chain.proceed(requestBuilder.build());
    }

}
