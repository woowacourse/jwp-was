package webserver.http;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class Cookie {
    private String name;
    private String value;
    private String expires;
    private String domain;
    private String path;
    private String secure;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        path = "/";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSecure() {
        return secure;
    }

    public void setSecure(String secure) {
        this.secure = secure;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s=%s ;", name, value));
        Arrays.stream(getClass().getDeclaredFields())
                .filter(f -> isNotNull(f) && isNotNameAndValue(f))
                .forEach(f -> sb.append(String.format("%s=%s ;", f.getName(), getFieldValue(f))));
        return sb.toString();
    }

    private String getFieldValue(Field field) {
        try {
            return (String) field.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isNotNameAndValue(Field field) {
        return (!"name".equals(field.getName())) && (!"value".equals(field.getName()));
    }

    private boolean isNotNull(Field field) {
        return getFieldValue(field) != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name) &&
                Objects.equals(value, cookie.value) &&
                Objects.equals(expires, cookie.expires) &&
                Objects.equals(domain, cookie.domain) &&
                Objects.equals(path, cookie.path) &&
                Objects.equals(secure, cookie.secure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, expires, domain, path, secure);
    }
}
