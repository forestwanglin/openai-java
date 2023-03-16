package com.felh.openai;

import com.felh.openai.model.Model;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenAiApi {

    @GET("v1/models")
    Single<OpenAiListResponse<Model>> listModels();

    @GET("/v1/models/{model_id}")
    Single<Model> getModel(@Path("model_id") String modelId);

}
