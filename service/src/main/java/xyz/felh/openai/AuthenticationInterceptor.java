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
    private final String openAiOrg;

    public AuthenticationInterceptor(String token) {
        this(token, null);
    }

    /**
     * Constructor
     *
     * @param token     OPENAI_API_KEY
     * @param openAiOrg OPENAI ORGANIZATION ID
     */
    public AuthenticationInterceptor(String token, String openAiOrg) {
        this.token = token;
        this.openAiOrg = openAiOrg;
    }

    @Override
    public @NonNull Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request()
                .newBuilder()
                .header("Authorization", "Bearer " + token);
        if (openAiOrg != null) {
            requestBuilder.header("OpenAI-Organization", openAiOrg);
        }
        return chain.proceed(requestBuilder.build());
    }

}
