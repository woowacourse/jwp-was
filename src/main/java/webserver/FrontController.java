package webserver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.MethodNotSupportException;
import http.HttpRequest;
import http.HttpResponse;
import http.ResponseStatusLine;

public class FrontController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final List<HandlerMapping> handlerMappings;

    public FrontController(final List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        try {
            Controller controller = getHandler(httpRequest);
            controller.service(httpRequest, httpResponse);
        } catch (MethodNotSupportException | HandlerNotFoundException exception) {
            logger.info(exception.getMessage());
            httpResponse.responseHeader(ResponseStatusLine.NOT_FOUND);
            httpResponse.noContent();
        } catch (AuthenticationException exception) {
            logger.info(exception.getMessage());
            httpResponse.responseHeader(ResponseStatusLine.UNAUTHORIZED);
            httpResponse.noContent();
        } catch (Exception exception) {
            logger.error("Unhandled exception occur. ", exception);
            httpResponse.responseHeader(ResponseStatusLine.INTERNAL_SERVER_ERROR);
            httpResponse.noContent();
        }
    }

    private Controller getHandler(final HttpRequest httpRequest) {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.matches(httpRequest))
                .findFirst()
                .map(HandlerMapping::getController)
                .orElseThrow(() -> new HandlerNotFoundException("요청에 맞는 handler를 찾을 수 없습니다."));
    }
}
