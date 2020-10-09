package webserver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import http.HttpRequest;
import http.HttpResponse;

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
        } catch (HandlerNotFoundException exception) {
            logger.info(exception.getMessage());
            httpResponse.response404Header();
            httpResponse.noContent();
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            httpResponse.response500Header();
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
