package http.request;

import http.request.bodyparser.FormDataParser;

import java.util.Map;

public class HttpFormDataMessageBody implements MessageBody {
    private final Map<String, String> formData;

    HttpFormDataMessageBody(final String body) {
        this.formData = FormDataParser.getInstance().parse(body);
    }

    @Override
    public String get(final String key) {
        return formData.get(key);
    }
}
