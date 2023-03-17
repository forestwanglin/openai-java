package com.felh.openai.completion;

import com.felh.openai.ApiEntityWithId;
import com.felh.openai.Usage;
import lombok.Data;

import java.util.List;

@Data
public class Completion extends ApiEntityWithId {

    private String model;

    private List<CompletionChoice> choices;

    private Usage usage;
}
