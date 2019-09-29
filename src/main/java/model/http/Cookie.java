package model.http;

import java.util.Objects;

public class Cookie {
    private String name;
    private String value;
    private String expires;
    private String domain;
    private String path;
    private String secure;

    public Cookie() {
        this.path = "/";
    }

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        this.path = "/";
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

    public boolean isLogined() {
        return value.equals("true");
    }

    public String toStringForHttpResponseHeader() {
        String cookieString = "Set-Cookie: " + name + "=" + value + "; ";
        if (expires != null) {
            cookieString += "expires=" + expires + "; ";
        }
        if (domain != null) {
            cookieString += "domain=" + domain + "; ";
        }
        if (path != null) {
            cookieString += "Path=" + path + "; ";
        }
        if (secure != null) {
            cookieString += "secure=" + secure + "; ";
        }
        return cookieString;
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
