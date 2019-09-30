package webserver.view;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.util.Objects;

public class RedirectView implements View {
    private final String path;

    public RedirectView(String path) {
        this.path = path;
    }

    @Override
    public void render(HttpRequest request, HttpResponse response) throws IOException {
        response.setLocation(path);
        response.found();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RedirectView that = (RedirectView) o;

        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }
}
