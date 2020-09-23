package request;

import java.util.Objects;

class MessageBody {

    static final String EMPTY_BODY = "";

    private String messageBody;

    MessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    String findDataFromFormFormatBody(String fieldName) {
        if (Objects.isNull(messageBody) || messageBody.isEmpty()) {
            throw new DataIsNotFormDataException("message body is empty.");
        }
        try {
            return new QueryData(messageBody).getValue(fieldName);
        } catch (IllegalArgumentException e) {
            throw new DataIsNotFormDataException("message body is not form data format.");
        }
    }

    @Override
    public String toString() {
        return messageBody;
    }
}
