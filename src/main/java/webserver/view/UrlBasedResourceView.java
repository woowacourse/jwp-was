package webserver.view;

import utils.FileLoader;
import webserver.file.File;
import webserver.message.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class UrlBasedResourceView implements View {
    private final String path;

    public UrlBasedResourceView(final String path) {
        this.path = path;
    }

    @Override
    public void render(Response response, Map<String, ?> models) {
        File file;
        try {
            file = FileLoader.loadFile(path);
        } catch (IOException | URISyntaxException e) {
            file = FileLoader.loadNotFoundFile();
        }
        response.body(file.getBody());
    }

}
