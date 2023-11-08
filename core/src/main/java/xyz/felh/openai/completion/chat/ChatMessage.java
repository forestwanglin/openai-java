package xyz.felh.openai.completion.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.completion.chat.tool.ToolCall;
import xyz.felh.openai.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    public static String IMG_DETAIL_LOW = "low";
    public static String IMG_DETAIL_HIGH = "high";


    public static String MSG_CONTENT_ITEM_TYPE_TEXT = "text";
    public static String MSG_CONTENT_ITEM_TYPE_IMAGE_URL = "image_url";

    public ChatMessage(ChatMessageRole role, Object content) {
        this.role = role;
        this.content = content;
    }

    /**
     * Must be either 'system', 'user', or 'assistant'.<br>
     * You may use {@link ChatMessageRole} enum.
     */
    private ChatMessageRole role;

    /**
     * The contents of message
     * string or array when role = user
     * otherwise string or null
     */
    private Object content;

    /**
     * The tool calls generated by the model, such as function calls.
     */
    private List<ToolCall> toolCalls;

    /**
     * Tool call that this message is responding to.
     */
    private String toolCallId;

    public void addText2ContentItem(String text) {
        List<MessageContentItem> list = getListExtractFromContent();
        list.add(MessageContentItem.buildText(text));
        this.content = list;
    }

    public void addImageWithBase642ContentItem(String base64) {
        addImageWithBase642ContentItem(base64, IMG_DETAIL_LOW);
    }

    public void addImageWithBase642ContentItem(String base64, String detail) {
        List<MessageContentItem> list = getListExtractFromContent();
        list.add(MessageContentItem.buildImageWithBase64(base64, detail));
        this.content = list;
    }

    public void addImageWithImageUrl2ContentItem(String imageUrl) {
        addImageWithImageUrl2ContentItem(imageUrl, IMG_DETAIL_LOW);
    }

    public void addImageWithImageUrl2ContentItem(String imageUrl, String detail) {
        List<MessageContentItem> list = getListExtractFromContent();
        list.add(MessageContentItem.buildImageWithUrl(imageUrl, detail));
        this.content = list;
    }

    private List<MessageContentItem> getListExtractFromContent() {
        if (Preconditions.isNotBlank(content)) {
            if (content instanceof List<?>) {
                return ((List<MessageContentItem>) content);
            }
        }
        return new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageUrl {
        private String url;
        private String detail; // low / high
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageContentItem {
        private String type;
        private String text;
        private ImageUrl imageUrl;

        private static MessageContentItem buildText(String text) {
            return MessageContentItem.builder()
                    .type(MSG_CONTENT_ITEM_TYPE_TEXT).text(text).build();
        }

        // 包括 data:image/jpeg;base64,
        private static MessageContentItem buildImageWithBase64(String base64, String detail) {
            return MessageContentItem.builder()
                    .type(MSG_CONTENT_ITEM_TYPE_IMAGE_URL)
                    .imageUrl(ImageUrl.builder()
                            .url(String.format("f\"%s\"", base64))
                            .detail(detail)
                            .build())
                    .build();
        }

        private static MessageContentItem buildImageWithUrl(String imageUrl, String detail) {
            return MessageContentItem.builder()
                    .type("image_url")
                    .imageUrl(ImageUrl.builder()
                            .url(imageUrl)
                            .detail(detail)
                            .build())
                    .build();
        }
    }

}
