package webserver.request;

import java.util.Map;

class MessageBody {

    private String messageBody;

    MessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Map<String, String> getFormDataFromBody() {
        System.out.println("*** message body ***");
        System.out.println(messageBody);
        System.out.println("********************");
        return new QueryData(messageBody).getQueryData();
    }

    public String getMessageBody() {
        return messageBody;
    }
}
