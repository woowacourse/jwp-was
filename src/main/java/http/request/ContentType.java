package http.request;

import http.exception.NotSupportContentTypeException;

import java.util.Arrays;
import java.util.function.Function;

public enum ContentType {
    FROM_URLENCODED("application/x-www-form-urlencoded", HttpFormDataMessageBody::new);

    private final String type;
    private final Function<String, MessageBody> messageBodyGenerator;

    ContentType(String type, Function<String, MessageBody> messageBodyGenerator) {
        this.type = type;
        this.messageBodyGenerator = messageBodyGenerator;
    }

    public static ContentType of(String type) {
        if (type == null) {
            return null;
        }

        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.type.equals(type))
                .findFirst()
                .orElseThrow(NotSupportContentTypeException::new);
    }

    public String getType() {
        return type;
    }

    public Function<String, MessageBody> getMessageBodyGenerator() {
        return messageBodyGenerator;
    }
}
