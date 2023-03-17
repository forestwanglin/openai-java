package com.felh.openai.image;

import com.felh.openai.IApiEntity;
import lombok.Data;

import java.util.List;

@Data
public class CreateImageResponse implements IApiEntity {

    private Long created;

    private List<Image> data;

}
