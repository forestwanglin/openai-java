package com.felh.openai.model;

import com.felh.openai.ApiEntityWithId;
import lombok.Data;

import java.util.List;

/**
 * List and describe the various models available in the API. You can refer to the Models documentation to understand what models are available and the differences between them.
 * <p>
 * See detail: https://platform.openai.com/docs/models
 */
@Data
public class Model extends ApiEntityWithId {

    private String ownedBy;

    private Long created;

    private List<Permission> permission;

    private String root;

    private String parent;

}
