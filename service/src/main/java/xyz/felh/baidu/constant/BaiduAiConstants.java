package xyz.felh.baidu.constant;

public interface BaiduAiConstants {


    String BASE_URL = "https://aip.baidubce.com";

    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_CONTENT_LENGTH = "Content-Length";
    String HEADER_CONTENT_MD5 = "Content-MD5";
    String HEADER_CONTENT_TYPE = "Content-Type";
    String HEADER_HOST = "Host";
    String HEADER_BCE_DATE = "x-bce-date";
    String HEADER_BCE_PREFIX = "x-bce-";

    String HEADER_X_APP_ID = "appid";
    /**
     * 一分钟内允许的最大请求次数
     */
    String HEADER_X_RATELIMIT_LIMIT_REQUESTS = "X-Ratelimit-Limit-Requests";
    /**
     * 一分钟内允许的最大tokens消耗，包含输入tokens和输出tokens
     */
    String HEADER_X_RATELIMIT_LIMIT_TOKENS = "X-Ratelimit-Limit-Tokens";
    /**
     * 达到RPM速率限制前，剩余可发送的请求数配额，如果配额用完，将会在0-60s后刷新
     */
    String HEADER_X_RATELIMIT_REMAINING_REQEUSTS = "X-Ratelimit-Remaining-Requests";
    /**
     * 达到TPM速率限制前，剩余可消耗的tokens数配额，如果配额用完，将会在0-60s后刷新
     */
    String HEADER_X_RATELIMIT_REMAINING_TOKENS = "X-Ratelimit-Remaining-Tokens";

}
