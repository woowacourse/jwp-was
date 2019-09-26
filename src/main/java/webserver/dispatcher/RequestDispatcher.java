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

    public void dispatch(HttpRequest httpRequest) throws IOException, URISyntaxException {
        HttpServlet httpServlet = MappingHandler.getServlets(httpRequest.getUri());
        View view = httpServlet.run(httpRequest, httpResponse);
        Resolver resolver = MappingHandler.getResolver(httpServlet);
        render(view, resolver);
    }

    private void render(View view, Resolver resolver) throws IOException, URISyntaxException {
        byte[] body = resolver.resolve(view.getName());
        if (body != null) {
            httpResponse.appendHeader("content-length", body.length);
        }
        httpResponse.writeHeader();
        httpResponse.writeBody(body);
        httpResponse.end();
    }
}
