package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.exception.NotFoundFileException;
import webserver.handler.MappingHandler;
import webserver.handler.exception.NotFoundURIException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.servlet.HttpServlet;
import webserver.servlet.exception.MethodNotAllowedException;
import webserver.view.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;

class DispatcherServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    public void doDispatch(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            HttpServlet httpServlet = MappingHandler.getServlets(httpRequest.getUri());
            ModelAndView mv = httpServlet.run(httpRequest, httpResponse);
            move(mv, httpRequest, httpResponse);
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

    private void move(ModelAndView mv, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        mv.getView().render(mv.getModelMap(), httpRequest, httpResponse);
    }
}
