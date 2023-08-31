package xyz.felh.openai.constant;

/**
 * OpenAI constants
 */
public interface OpenAiConstants {

    String BASE_URL = "https://api.openai.com";

    /***********  headers' name from response *************/

    String HEADER_OPENAI_MODEL = "openai-model";
    String HEADER_OPENAI_ORGANIZATION = "openai-organization";
    String HEADER_OPENAI_PROCESSING_MS = "openai-processing-ms";
    String HEADER_OPENAI_VERSION = "openai-version";


    /**
     * The maximum number of requests that are permitted before exhausting the rate limit.
     * Unit: RPM
     */
    String HEADER_X_RATELIMIT_LIMIT_REQUESTS = "x-ratelimit-limit-requests";

    /**
     * The maximum number of tokens that are permitted before exhausting the rate limit.
     * Unit: TPM
     */
    String HEADER_X_RATELIMIT_LIMIT_TOKENS = "x-ratelimit-limit-tokens";

    /**
     * The remaining number of requests that are permitted before exhausting the rate limit.
     */
    String HEADER_X_RATELIMIT_REMAINING_REQUESTS = "x-ratelimit-remaining-requests";

    /**
     * The remaining number of tokens that are permitted before exhausting the rate limit.
     */
    String HEADER_X_RATELIMIT_REMAINING_TOKENS = "x-ratelimit-remaining-tokens";

    /**
     * The time until the rate limit (based on requests) resets to its initial state.
     */
    String HEADER_X_RATELIMIT_RESET_REQUESTS = "x-ratelimit-reset-requests";

    /**
     * The time until the rate limit (based on tokens) resets to its initial state.
     */
    String HEADER_X_RATELIMIT_RESET_TOKENS = "x-ratelimit-reset-tokens";

    String HEADER_X_REQUEST_ID = "x-request-id";

}
