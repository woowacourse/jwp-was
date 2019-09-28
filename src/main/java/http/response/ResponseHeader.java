package http.response;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

public class ResponseHeader {
    private String type;
    private String location;
    private Cookies cookies;

    public ResponseHeader() {
        this.cookies = new Cookies(Lists.newArrayList());
    }

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

    public List<Cookie> getCookies() {
        return this.cookies.getCookies();
    }

    public void setCookie(Cookie cookie) {
        this.cookies.add(cookie);
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
