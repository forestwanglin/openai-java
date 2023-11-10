package xyz.felh.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiBean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestResponseFormat implements IOpenAiBean {

    /**
     * Must be one of text or json_object.
     * <p>
     * String, Optional
     * <p>
     * Defaults to text
     */
    private String type;

}
