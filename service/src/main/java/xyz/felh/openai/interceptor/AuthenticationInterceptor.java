package xyz.felh.openai.interceptor;

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
    private final String projectId;

    /**
     * Constructor
     *
     * @param token OPENAI_API_KEY
     */
    public AuthenticationInterceptor(String token) {
        this(token, null, null);
    }

    /**
     * Constructor
     *
     * @param token     OPENAI_API_KEY
     * @param orgId     ORGANIZATION ID
     * @param projectId PROJECT ID
     */
    public AuthenticationInterceptor(String token, String orgId, String projectId) {
        this.token = token;
        this.orgId = orgId;
        this.projectId = projectId;
    }

    @Override
    public @NonNull Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request()
                .newBuilder()
                .header("Authorization", "Bearer " + token);
        if (orgId != null) {
            requestBuilder.header("OpenAI-Organization", orgId);
        }
        if (projectId != null) {
            requestBuilder.header("OpenAI-Project", projectId);
        }
        // https://platform.openai.com/docs/assistants/overview
        if (chain.request().url().url().getPath().startsWith("/v1/assistants")
                || chain.request().url().url().getPath().startsWith("/v1/threads")) {
            requestBuilder.header("OpenAI-Beta", "assistants=v1");
        }
        return chain.proceed(requestBuilder.build());
    }

}
