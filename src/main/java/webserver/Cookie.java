package webserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import utils.StringUtils;

public class Cookie {

    private static final String MAX_AGE_HEADER = "max-age=";
    private static final String HTTP_ONLY_HEADER = "HttpOnly";
    private static final String PATH_HEADER = "Path=";
    private static final String COOKIE_HEADER_DELIMITER = "; ";
    private static final int INIT_MAX_AGE = -1;

    private final String name;
    private String value;
    private String path;
    private int maxAge = -1;
    private boolean httpOnly;

    public Cookie(String name, String value) {
        validate(name);
        this.name = name;
        this.value = value;
    }

    private void validate(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("쿠키의 이름 : " + name + "이 잘못됐습니다!");
        }
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public String generateHeader() {
        List<String> header = new ArrayList<>();
        generateCookieValueString(header);
        generatePathString(header);
        generateMaxAgeString(header);
        generateHttpOnlyString(header);

        return String.join(COOKIE_HEADER_DELIMITER, header);
    }

    private boolean isPath() {
        return Objects.nonNull(this.path);
    }

    public void setPath(String path) {
        this.path = path;
    }

    private boolean isMaxAge() {
        return this.maxAge != INIT_MAX_AGE;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    private void generateCookieValueString(List<String> header) {
        header.add(this.name + "=" + this.value);
    }

    private void generatePathString(List<String> header) {
        if (isPath()) {
            header.add(PATH_HEADER + this.path);
        }
    }

    private void generateMaxAgeString(List<String> header) {
        if (isMaxAge()) {
            header.add(MAX_AGE_HEADER + this.maxAge);
        }
    }

    private void generateHttpOnlyString(List<String> header) {
        if (isHttpOnly()) {
            header.add(HTTP_ONLY_HEADER);
        }
    }
}
