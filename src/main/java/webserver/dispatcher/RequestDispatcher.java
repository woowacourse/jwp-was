package webserver.dispatcher;

import exceptions.MethodNotAllowedException;
import exceptions.NotFoundFileException;
import exceptions.NotFoundURIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.MappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.resolver.Resolver;
import webserver.servlet.HttpServlet;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public class RequestDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);
    private HttpResponse httpResponse;

    public RequestDispatcher(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public void dispatch(HttpRequest httpRequest) throws IOException {
        View view = new View();
        try {
            HttpServlet httpServlet = MappingHandler.getServlets(httpRequest.getUri());
            ModelAndView modelAndView = httpServlet.run(httpRequest, httpResponse);
            Resolver resolver = MappingHandler.getResolver(httpServlet);
            view = resolver.resolve(modelAndView);
        } catch (NotFoundFileException | NotFoundURIException e) {
            logger.error(e.getMessage());
            httpResponse.error(HttpStatus.NOT_FOUND);
        } catch (MethodNotAllowedException e) {
            logger.error(e.getMessage());
            httpResponse.error(HttpStatus.METHOD_NOT_ALLOW);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            httpResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        render(view);
    }

    private void render(View view) throws IOException {
        byte[] body = view.getBody();
        httpResponse.writeLine();
        if (view.isViewExists()) {
            httpResponse.appendHeader("content-length", body.length);
        }
        httpResponse.writeHeader();
        httpResponse.writeBody(body);
        httpResponse.end();
    }

}
