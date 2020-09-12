package webserver.request;

import java.util.Map;

class MessageBody {

    private String messageBody;

    MessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Map<String, String> getFormDataFromBody() {
        return new QueryData(messageBody).getQueryData();
    }

    public String getMessageBody() {
        return messageBody;
    }
}
