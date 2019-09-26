package http.response;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ResponseHeader {
    private String type;
    private String location;
    private Cookies cookies = new Cookies();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Map.Entry<String, Cookie>> getCookie() {
        return cookies.getCookies();
    }

    public void setCookie(String key, Cookie cookie) {
        cookies.setCookie(key, cookie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseHeader that = (ResponseHeader) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, location);
    }
}
