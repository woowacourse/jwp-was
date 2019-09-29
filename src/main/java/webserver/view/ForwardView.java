package webserver.view;

import utils.FileIoUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.FileType;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class ForwardView implements View {
    String name;

    public ForwardView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(name);
        httpResponse.ok();
        httpResponse.appendContentHeader(FileType.HTML.getMimeName(), body.length);
        send(httpResponse, body);
    }
}
