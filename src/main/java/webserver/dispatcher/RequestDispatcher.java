package webserver.dispatcher;

import webserver.handler.MappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resolver.Resolver;
import webserver.servlet.HttpServlet;
import webserver.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public class RequestDispatcher {
    HttpResponse httpResponse;

    public RequestDispatcher(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public void dispatch(HttpRequest httpRequest) throws IOException {
        HttpServlet httpServlet = MappingHandler.getServlets(httpRequest.getUri());
        View view = httpServlet.run(httpRequest, httpResponse);
        Resolver resolver = MappingHandler.getResolver(httpServlet);
        byte[] body = getBytes(view, resolver);
        render(body);
    }

    private void render(byte[] body) throws IOException {
        httpResponse.writeLine();
        if (body.length > 0) {
            httpResponse.appendHeader("content-length", body.length);
        }
        httpResponse.writeHeader();
        httpResponse.writeBody(body);
        httpResponse.end();
    }

    private byte[] getBytes(View view, Resolver resolver) throws IOException {
        byte[] body = null;
        try {
            body = resolver.resolve(view.getName());
        } catch (NullPointerException e) {
            httpResponse.error();
        } catch (URISyntaxException e) {
            httpResponse.error();
        }
        return body;
    }
}
