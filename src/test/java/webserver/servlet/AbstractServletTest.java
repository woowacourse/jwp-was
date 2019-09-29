package webserver.servlet;

import helper.IOHelper;
import webserver.http.request.HttpRequest;
import webserver.parser.HttpRequestParser;

import java.io.IOException;

public abstract class AbstractServletTest {
    protected HttpRequest getFileRequest(String url) throws IOException {
        return HttpRequestParser.parse(IOHelper.createBuffer(
                "GET " + url + " HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        ));
    }
}
