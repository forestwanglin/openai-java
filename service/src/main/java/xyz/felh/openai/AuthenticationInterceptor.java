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

    public AuthenticationInterceptor(String token) {
        this.token = token;
    }

    @Override
    public @NonNull Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();
        return chain.proceed(request);
    }

}
