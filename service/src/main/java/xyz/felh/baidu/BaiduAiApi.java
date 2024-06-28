package xyz.felh.baidu;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import xyz.felh.baidu.chat.ChatCompletion;
import xyz.felh.baidu.chat.CreateChatCompletionRequest;

/**
 * Retrofit2 API interface
 */
public interface BaiduAiApi {

    @POST("/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/{model_path}")
    Single<ChatCompletion> createChat(@Path("model_path") String path, @Body CreateChatCompletionRequest request);

}
