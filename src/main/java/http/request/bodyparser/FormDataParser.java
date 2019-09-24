package http.request.bodyparser;

import http.request.HttpRequestBody;
import http.request.MessageBodyParser;

import java.util.HashMap;
import java.util.Map;

public class FormDataParser implements MessageBodyParser {
    private static final String FORM_DATA_DELIMITER = "&";
    private static final String VALUE_DELIMITER = "=";

    @Override
    public Map<String, String> parse(HttpRequestBody body) {
        Map<String, String> formData = new HashMap<>();
        String[] values = body.getBody().split(FORM_DATA_DELIMITER);

        for (String value : values) {
            String[] token = value.split(VALUE_DELIMITER);
            if (token.length != 2) {
                formData.put(token[0], "");
            } else {
                formData.put(token[0], token[1]);
            }
        }

        return formData;
    }
}
