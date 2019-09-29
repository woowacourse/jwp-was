package webserver.view;

import com.github.jknack.handlebars.Template;
import webserver.http.request.HttpRequest;
import webserver.http.response.FileType;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

public class HandlebarView implements View {
    private String name;
    private Template template;

    public HandlebarView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = template.apply(model).getBytes();
        httpResponse.ok();
        httpResponse.appendContentHeader(FileType.HTML.getMimeName(), body.length);
        send(httpResponse, body);
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlebarView that = (HandlebarView) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(template, that.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, template);
    }
}
