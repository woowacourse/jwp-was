package webserver.view;

import com.github.jknack.handlebars.Template;
import webserver.http.request.HttpRequest;
import webserver.http.response.FileType;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class HandlebarView implements View {
    String name;
    Template template;

    public HandlebarView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = template.apply(model).getBytes();
        httpResponse.ok();
        httpResponse.appendContentHeader(FileType.HTML.getMemeName(), body.length);
        send(httpResponse, body);
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
}
