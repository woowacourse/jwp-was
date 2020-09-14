package http.request;

import java.util.Map;
import java.util.Objects;

public class RequestParams {
    private final Map<String, String> params;

    public RequestParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestParams that = (RequestParams) o;
        return Objects.equals(getParams(), that.getParams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParams());
    }
}
