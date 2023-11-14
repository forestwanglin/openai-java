package xyz.felh.openai.thread;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiApiRequest;
import xyz.felh.openai.thread.message.Message;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateThreadRequest implements IOpenAiApiRequest {

    /**
     * A list of {@link Message} to start the thread with.
     * <p>
     * required fields: role, content
     */
    private List<Message> messages;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    private Map<String, String> metadata;

}