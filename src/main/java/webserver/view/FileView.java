package webserver.view;

import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class FileView implements View {
    String name;

    public FileView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(name);

        httpResponse.ok();
        httpResponse.writeLine();

        httpResponse.appendHeader("content-type", "text/html");
        httpResponse.appendHeader("content-length", body.length);
        httpResponse.writeHeader();

        httpResponse.writeBody(body);

        httpResponse.end();
    }
}
