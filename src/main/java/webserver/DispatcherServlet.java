package webserver;

import exceptions.MethodNotAllowedException;
import exceptions.NotFoundFileException;
import exceptions.NotFoundURIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.MappingHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.servlet.HttpServlet;
import webserver.view.ModelAndView;
import webserver.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public class DispatcherServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public void doDispatch(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            HttpServlet httpServlet = MappingHandler.getServlets(httpRequest.getUri());
            ModelAndView modelAndView = httpServlet.run(httpRequest, httpResponse);
            View view = modelAndView.getView();
            view.render(modelAndView.getModelMap(), httpRequest, httpResponse);
        } catch (NotFoundFileException | NotFoundURIException e) {
            logger.error(e.getMessage());
            httpResponse.error(HttpStatus.NOT_FOUND);
            httpResponse.errorWrite();
        } catch (MethodNotAllowedException e) {
            logger.error(e.getMessage());
            httpResponse.error(HttpStatus.METHOD_NOT_ALLOW);
            httpResponse.errorWrite();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            httpResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
            httpResponse.errorWrite();
        }
    }
}
