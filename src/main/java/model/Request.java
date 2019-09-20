package model;

import exception.NotFoundRequestElementException;

import java.util.Map;
import java.util.Objects;

public class Request {
    private Map<String, String> header;
    private Map<String, String> parameter;

    public Request(Map<String, String> header, Map<String, String> parameter) {
        this.header = header;
        this.parameter = parameter;
    }

    public String getUrl() {
        return header.get("Method").split(" ")[1];
    }

    public String getHeader(String key) {
        String element = header.get(key);

        if (Objects.isNull(element)) {
            throw new NotFoundRequestElementException();
        }
        return element;
    }

    public String getMethod() {
        return header.get("Method").split(" ")[0];
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }
}
