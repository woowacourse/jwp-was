package webserver.view;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public interface View {
    void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;

    default void send(HttpResponse httpResponse, byte[] body) throws IOException {
        httpResponse.writeLine();
        httpResponse.writeHeader();
        httpResponse.writeBody(body);
        httpResponse.end();
    }

    default void send(HttpResponse httpResponse) throws IOException {
        httpResponse.writeLine();
        httpResponse.writeHeader();
        httpResponse.end();
    }
}
