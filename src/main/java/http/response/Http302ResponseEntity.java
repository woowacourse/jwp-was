package http.response;

import http.HttpHeaders;

import java.util.Objects;

import static http.HttpVersion.DEFAULT_VERSION;
import static http.response.HttpStatus.FOUND;

public class Http302ResponseEntity implements HttpResponseEntity {
    private String location;

    public Http302ResponseEntity(String location) {
        this.location = location;
    }

    @Override
    public HttpResponse makeResponse() {
        HttpStatusLine statusLine = new HttpStatusLine(DEFAULT_VERSION, FOUND);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Location", location);
        return new HttpResponse(statusLine, headers, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Http302ResponseEntity that = (Http302ResponseEntity) o;
        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
