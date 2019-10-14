package webserver.view;

import webserver.message.response.Response;

import java.util.Map;

public class RedirectView implements View {
    private final String path;

    public RedirectView(String path) {
        this.path = path;
    }

    @Override
    public void render(Response response, Map<String, ?> models) {
        response.redirect(path);
    }
}
