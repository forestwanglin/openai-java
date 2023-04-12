/*
 * Copyright (c) 2023 Mariusz Bernacki <info@didalgo.com>
 * SPDX-License-Identifier: MIT
 */
package xyz.felh.openai.gpt3.tokenizer;

import lombok.Data;

import java.util.Objects;

/**
 * Describes the various chat messaging formats for the purpose of counting tokens in chat conversations against different models.
 */
@Data
public class ChatFormatDescriptor {

    private Encoding encoding;
    private int extraTokenCountPerMessage;
    private int extraTokenCountPerRequest;

    public static ChatFormatDescriptor forModel(String modelName) {
        switch (modelName) {
            case "gpt-3.5-turbo":
                return forModel("gpt-3.5-turbo-0301");
            case "gpt-4":
                return forModel("gpt-4-0314");
            case "gpt-3.5-turbo-0301":
                return new ChatFormatDescriptor(Encoding.forModel(modelName), 4, 3);
            case "gpt-4-0314":
                return new ChatFormatDescriptor(Encoding.forModel(modelName), 3, 3);
            default:
                throw new IllegalArgumentException(String.format("Model `%s` not found", modelName));
        }
    }

    private ChatFormatDescriptor(Encoding encoding, int extraTokenCountPerMessage, int extraTokenCountPerRequest) {
        Objects.requireNonNull(encoding, "encoding");
        this.encoding = encoding;
        this.extraTokenCountPerMessage = extraTokenCountPerMessage;
        this.extraTokenCountPerRequest = extraTokenCountPerRequest;
    }

}
