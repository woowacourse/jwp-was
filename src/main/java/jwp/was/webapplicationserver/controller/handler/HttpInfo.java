package jwp.was.webapplicationserver.controller.handler;

import java.util.Objects;
import jwp.was.webserver.HttpMethod;

public class HttpInfo {

    private HttpMethod method;
    private String urlPath;

    private HttpInfo(HttpMethod method, String urlPath) {
        this.method = method;
        this.urlPath = urlPath;
    }

    public static HttpInfo of(HttpMethod method, String urlPath) {
        return new HttpInfo(method, urlPath);
    }

    public static HttpInfo of(String method, String urlPath) {
        return new HttpInfo(HttpMethod.from(method), urlPath);
    }

    public boolean isSamePath(String urlPath) {
        return this.urlPath.equals(urlPath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpInfo httpInfo = (HttpInfo) o;
        return method == httpInfo.method &&
            Objects.equals(urlPath, httpInfo.urlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, urlPath);
    }

    @Override
    public String toString() {
        return "HttpInfo{" +
            "method=" + method +
            ", urlPath='" + urlPath + '\'' +
            '}';
    }
}
