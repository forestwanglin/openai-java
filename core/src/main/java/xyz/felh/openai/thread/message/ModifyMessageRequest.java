package xyz.felh.openai.thread.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiApiRequest;

import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ModifyMessageRequest implements IOpenAiApiRequest {

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    private Map<String, String> metadata;

}