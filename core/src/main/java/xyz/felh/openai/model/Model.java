package xyz.felh.openai.model;

import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;
import lombok.Data;

import java.util.List;

/**
 * List and describe the various models available in the API.
 * You can refer to the Models documentation to understand what models are available and the differences between them.
 * See detail: https://platform.openai.com/docs/models
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Model extends OpenAiApiObjectWithId {

    public Long created;

    private String ownedBy;

    private List<Permission> permission;

    private String root;

    private String parent;

}
