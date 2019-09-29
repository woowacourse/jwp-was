package webserver.view;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class RedirectView implements View {
    String name;

    public RedirectView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        httpResponse.redirect(name);
        send(httpResponse);
    }
}
