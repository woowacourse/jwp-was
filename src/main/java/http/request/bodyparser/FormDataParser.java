package http.request.bodyparser;

import http.request.HttpRequestBody;
import http.request.MessageBodyParser;

import java.util.HashMap;
import java.util.Map;

public class FormDataParser implements MessageBodyParser {
    private static final String FORM_DATA_DELIMITER = "&";
    private static final String VALUE_DELIMITER = "=";
    private static final String NONE_VALUE = "";
    private static final int EXIST_VALUE_LENGTH = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    @Override
    public Map<String, String> parse(HttpRequestBody body) {
        Map<String, String> formData = new HashMap<>();
        String[] values = body.getBody().split(FORM_DATA_DELIMITER);

        for (String value : values) {
            String[] token = value.split(VALUE_DELIMITER);
            if (token.length != EXIST_VALUE_LENGTH) {
                formData.put(token[KEY_INDEX], NONE_VALUE);
            } else {
                formData.put(token[KEY_INDEX], token[VALUE_INDEX]);
            }
        }

        return formData;
    }
}
