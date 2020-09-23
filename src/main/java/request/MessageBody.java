package request;

import java.util.Objects;

public class MessageBody {

    static final String EMPTY_BODY = "";

    private String messageBody;

    public MessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String findDataFromFormFormatBody(String fieldName) {
        if (Objects.isNull(messageBody) || messageBody.isEmpty()) {
            throw new DataIsNotFormDataException("message body is empty.");
        }
        try {
            return new QueryData(messageBody).getValue(fieldName);
        } catch (IllegalArgumentException e) {
            throw new DataIsNotFormDataException("message body is not form data format.");
        }
    }

    public String getMessageBody() {
        return messageBody;
    }

    @Override
    public String toString() {
        return messageBody;
    }
}
