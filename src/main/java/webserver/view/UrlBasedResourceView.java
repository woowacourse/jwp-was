package webserver.view;

import utils.FileLoader;
import webserver.StaticFile;
import webserver.message.response.Response;

import java.util.Map;

public class UrlBasedResourceView implements View {
    private final String path;

    public UrlBasedResourceView(final String path) {
        this.path = path;
    }

    @Override
    public void render(Response response, Map<String, ?> models) {
        StaticFile staticFile = FileLoader.loadStaticFile(path);
        response.body(staticFile.getBody());
    }

}
