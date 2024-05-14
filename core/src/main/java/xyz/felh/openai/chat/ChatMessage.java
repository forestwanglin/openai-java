package xyz.felh.openai.chat;


import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.chat.tool.ToolCall;
import xyz.felh.openai.utils.ListUtils;
import xyz.felh.openai.utils.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements IOpenAiBean {

    public ChatMessage(@NonNull ChatMessageRole role, Object content) {
        this(role, null, content);
    }

    public ChatMessage(@NonNull ChatMessageRole role, String name, Object content) {
        this.role = role;
        this.name = name;
        this.content = content;
    }

    /**
     * Must be either 'system', 'user', 'assistant', or 'tool'.<br>
     * You may use {@link ChatMessage} enum.
     */
    @NonNull
    @JSONField(name = "role")
    @JsonProperty("role")
    private ChatMessageRole role;

    /**
     * The contents of the message
     * <p>
     * 1. role=system, string or null, required<br/>
     * 2. role=user, string or array, required<br/>
     * 3. role=assistant, string or null, Required unless tool_calls is specified.<br/>
     * 4. role=tool, string or null, required<br/>
     */
    @JSONField(name = "content")
    @JsonProperty("content")
    private Object content;

    /**
     * An optional name for the participant. Provides the model information to differentiate between participants of the same role.
     * <p>
     * Optional
     * <p>
     * Do not provide this field when role is tool.
     */
    @JSONField(name = "name")
    @JsonProperty("name")
    private String name;

    /**
     * The tool calls generated by the model, such as function calls.
     * <p>
     * Array, Optional
     * It is provided when role is assistant
     * <p>
     * See {@link ToolCall}
     */
    @JSONField(name = "tool_calls")
    @JsonProperty("tool_calls")
    private List<ToolCall> toolCalls;

    /**
     * Tool call that this message is responding to.
     * <p>
     * String, Required
     * It is provided when role is {@link ChatMessageRole#TOOL}
     */
    @JSONField(name = "tool_call_id")
    @JsonProperty("tool_call_id")
    private String toolCallId;

    public void addTextToContent(String text) {
        List<ContentItem> list = getListExtractFromContent();
        list.add(ContentItem.buildText(text));
        this.content = list;
    }

    public void addImageBase64ToContent(String base64) {
        addImageBase64ToContent(base64, ImageUrlDetail.LOW);
    }

    public void addImageBase64ToContent(String base64, ImageUrlDetail detail) {
        List<ContentItem> list = getListExtractFromContent();
        list.add(ContentItem.buildImageWithBase64(base64, detail));
        this.content = list;
    }

    public void addImageUrlToContent(String imageUrl) {
        addImageUrlToContent(imageUrl, ImageUrlDetail.LOW);
    }

    public void addImageUrlToContent(String imageUrl, ImageUrlDetail detail) {
        List<ContentItem> list = getListExtractFromContent();
        list.add(ContentItem.buildImageWithUrl(imageUrl, detail));
        this.content = list;
    }

    private List<ContentItem> getListExtractFromContent() {
        if (Preconditions.isNotBlank(content)) {
            if (content instanceof List<?>) {
                return ListUtils.castList(content, ContentItem.class);
            }
        }
        return new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageUrl implements IOpenAiBean {
        /**
         * Either a URL of the image or the base64 encoded image data.
         */
        @NonNull
        @JSONField(name = "url")
        @JsonProperty("url")
        private String url;
        /**
         * Specifies the detail level of the image.
         * <p>
         * Optional, Defaults to auto
         */
        @JSONField(name = "detail")
        @JsonProperty("detail")
        private ImageUrlDetail detail;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContentItem implements IOpenAiBean {

        /**
         * The type of the content part.
         */
        @JSONField(name = "type")
        @JsonProperty("type")
        private ContentType type;

        /**
         * The text content.
         */
        @JSONField(name = "text")
        @JsonProperty("text")
        private String text;

        /**
         * Image url
         */
        @JSONField(name = "image_url")
        @JsonProperty("image_url")
        private ImageUrl imageUrl;

        public static ContentItem buildText(String text) {
            return ContentItem.builder().type(ContentType.TEXT).text(text).build();
        }

        // 包括 data:image/jpeg;base64,
        public static ContentItem buildImageWithBase64(String base64, ImageUrlDetail detail) {
            return ContentItem.builder()
                    .type(ContentType.IMAGE_URL)
                    .imageUrl(ImageUrl.builder().url(String.format("f\"%s\"", base64)).detail(detail).build())
                    .build();
        }

        public static ContentItem buildImageWithUrl(String imageUrl, ImageUrlDetail detail) {
            return ContentItem.builder()
                    .type(ContentType.IMAGE_URL)
                    .imageUrl(ImageUrl.builder().url(imageUrl).detail(detail).build())
                    .build();
        }

    }

    @Getter
    public enum ContentType {

        TEXT("text"),
        IMAGE_URL("image_url");

        private final String value;

        ContentType(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static ContentType findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }
    }

    @Getter
    public enum ImageUrlDetail {

        LOW("low"),
        HIGH("high");

        private final String value;

        ImageUrlDetail(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static ImageUrlDetail findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }
    }

}
