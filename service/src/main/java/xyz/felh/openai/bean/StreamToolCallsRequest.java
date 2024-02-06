package xyz.felh.openai.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.chat.CreateChatCompletionRequest;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StreamToolCallsRequest implements Serializable {

    private String requestId;

    private CreateChatCompletionRequest request;

}