package request;

import java.util.Map;

public class MessageBody {

    private String messageBody;

    public MessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Map<String, String> getFormDataFromBody() {
        return new QueryData(messageBody).getQueryData();
    }

    public String getMessageBody() {
        return messageBody;
    }
}
