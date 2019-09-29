package webserver.servlet;

import helper.IOHelper;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.parser.HttpRequestParser;
import webserver.resolver.Resolver;

import java.io.IOException;

public abstract class AbstractServletTest {
    HttpResponse httpResponse;
    AbstractRequestServlet httpServlet;
    Resolver resolver;

    protected HttpRequest getCommonGetRequest(String url) throws IOException {
        return HttpRequestParser.parse(IOHelper.createBuffer(
                "GET " + url + " HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        ));
    }
}
