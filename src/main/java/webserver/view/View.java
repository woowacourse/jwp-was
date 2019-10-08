package webserver.view;

import webserver.message.response.Response;

import java.util.Map;

public interface View {
    void render(final Response response, final Map<String, ?> models);
}
