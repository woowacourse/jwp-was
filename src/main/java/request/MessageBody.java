package request;

import java.util.Map;
import java.util.Objects;

public class MessageBody {

    private String messageBody;

    public MessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Map<String, String> getFormDataFromBody() {
        if (Objects.isNull(messageBody) || messageBody.isEmpty()) {
            throw new UnsupportedOperationException("message body is empty.");
        }
        try {
            return new QueryData(messageBody).getQueryData();
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException("message body is not form data format.");
        }
    }

    public String getMessageBody() {
        return messageBody;
    }
}
