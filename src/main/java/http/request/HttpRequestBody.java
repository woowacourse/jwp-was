package http.request;

import java.util.List;
import java.util.Objects;

public class HttpRequestBody {
    List<String> body;

    public HttpRequestBody(List<String> body) {
        this.body = body;
    }

    public List<String> getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestBody that = (HttpRequestBody) o;
        return body.equals(that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }
}
