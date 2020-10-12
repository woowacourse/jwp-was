package webserver.response;

import utils.StringUtils;

public class Cookie {

    private final String name;
    private String value;
    private int maxAge = -1;
    private boolean httpOnly;

    public Cookie(String name, String value) {
        validate(name);
        this.name = name;
        this.value = value;
    }

    private void validate(String name) {
        if(StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("쿠키의 이름 : " + name + "이 잘못됐습니다!");
        }
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

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }
}
