package webserver.view;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class ForwardView implements View {
    String name;

    public ForwardView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] body =
        httpResponse.writeLine();
        httpResponse.appendHeader("content-length", body.length);
        httpResponse.writeHeader();
        httpResponse.writeBody(body);
        httpResponse.end();
    }
}
