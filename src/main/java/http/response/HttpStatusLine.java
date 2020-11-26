package http.response;

import http.HttpStatus;
import java.util.Objects;

public class HttpStatusLine {
    private final String version;
    private HttpStatus status;

    private HttpStatusLine(String version, HttpStatus status) {
        this.version = version;
        this.status = status;
    }

    public static HttpStatusLine from(String version) {
        return new HttpStatusLine(version, HttpStatus.OK);
    }

    protected HttpStatusLine setStatus(HttpStatus status) {
        this.status = Objects.requireNonNull(status, "상태 코드는 null이 될 수 없습니다.");
        return this;
    }

    public String build() {
        return version + " " + status.build();
    }
}
