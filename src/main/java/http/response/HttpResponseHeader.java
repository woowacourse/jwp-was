package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HttpResponseHeader {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseHeader.class);

    private List<HttpResponseField> fields;

    public HttpResponseHeader() {
        fields = new ArrayList<>();
    }

    public HttpResponseHeader(final String headers) {
        this.fields = new ArrayList<>();
        String[] field = headers.split("\n");

        for (String s : field) {
            String[] keyValue = s.split(": ");
            this.fields.add(new HttpResponseField(keyValue[0], keyValue[1]));
        }
    }

    public void addCookie(String value) {
        fields.add(new HttpResponseField("Set-Cookie", value));
    }

    public void addField(String key, String value) {
        HttpResponseField httpResponseField = new HttpResponseField(key, value);

        if (fields.contains(httpResponseField)) {
            int index = fields.indexOf(httpResponseField);
            fields.get(index).updateValue(value);
            return;
        }

        fields.add(httpResponseField);
    }

    public void setLocation(String uri) {
        logger.debug("redirect uri: {}", uri);
        addField("Location", uri);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        fields.forEach(field -> stringBuilder.append(field.toString()).append("\r\n"));
        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }
}
