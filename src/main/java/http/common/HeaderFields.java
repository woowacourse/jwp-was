package http.common;

import http.exception.InvalidHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HeaderFields {
    private static final Logger logger = LoggerFactory.getLogger(HeaderFields.class);
    private static final String HEADER_FIELD_KEY_VALUE_DELIMITER = ":";

    private final Map<String, String> headerFields;
    private Map<String, Cookie> cookies;

    public HeaderFields(List<String> headerFields) {
        if (headerFields == null) {
            throw new InvalidHeaderException("headerFields를 생성할 수 없습니다.");
        }
        this.headerFields = new HashMap<>();
        this.cookies = new HashMap<>();
        for (String headerField : headerFields) {
            String key = headerField.substring(0, headerField.indexOf(HEADER_FIELD_KEY_VALUE_DELIMITER));
            String value = headerField.substring(headerField.indexOf(HEADER_FIELD_KEY_VALUE_DELIMITER) + 2);
            if(key.equals("Cookie")) {
                Cookie cookie =  new Cookie(value);
                cookies.put(cookie.getName(), cookie);
            } else{
                this.headerFields.put(key, value);
            }
        }
    }

    public String convert() {
        StringBuilder sb = new StringBuilder();
        for (String field : headerFields.keySet()) {
            sb.append(field).append(": ").append(headerFields.get(field)).append("\r\n");
        }
        for (String cookie : cookies.keySet()) {
            sb.append("Set-Cookie: ").append(cookies.get(cookie).getFieldString());
        }
        return sb.toString();
    }

    public String getHeader(String fieldName) {
        if (headerFields.containsKey(fieldName)) {
            return headerFields.get(fieldName);
        }
        logger.error("Response Header에 " + fieldName + "이 존재하지않습니다.");
        throw new InvalidHeaderException(fieldName + "를 찾을 수 없습니다.");
    }

    public Cookie getCookie(String cookieName) {
        if (!cookies.containsKey(cookieName)) {
            logger.error(cookieName + "라는 쿠키는 존재하지 않습니다.");
            throw new InvalidHeaderException("존재하지 않는 쿠키입니다.");
        }
        return cookies.get(cookieName);
    }

    public int getContentLength() {
        return Integer.parseInt(headerFields.getOrDefault("Content-Length", String.valueOf(0)));
    }

    public void addHeader(String fieldName, String field) {
        headerFields.put(fieldName, field);
    }


    public void addCookie(String name, String value) {
        cookies.put(name, new Cookie(name, value));
    }

    public void addCookieOption(String name, String option, String value) {
        if (!cookies.containsKey(name)) {
            throw new InvalidHeaderException("존재하지 않는 쿠키입니다.");
        }
        cookies.get(name).addOption(option, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderFields fields = (HeaderFields) o;
        return headerFields.equals(fields.headerFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerFields);
    }

    @Override
    public String toString() {
        return convert();
    }
}
