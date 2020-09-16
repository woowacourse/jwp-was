package webserver;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

public class FrontController {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final Map<RequestMapping, Handler> handlerMappings = new LinkedHashMap<>();

    static {
        UserController userController = new UserController();
        handlerMappings.put(new RequestMapping("/user/create", HttpMethod.POST), userController::saveUser);
    }

    public void doService(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        try {
            Handler handler = getHandler(httpRequest);
            handler.handleRequest(httpRequest, httpResponse);
        } catch (HandlerNotFoundException exception) {
            logger.info(exception.getMessage());
            httpResponse.response404Header();
        } catch (Exception exception) {
            httpResponse.response500Header();
        }
    }

    public Handler getHandler(final HttpRequest httpRequest) {
        return handlerMappings.keySet()
                .stream()
                .filter(requestMapping -> requestMapping.matches(httpRequest))
                .findFirst()
                .map(handlerMappings::get)
                .orElseThrow(() -> new HandlerNotFoundException("요청에 맞는 handler를 찾을 수 없습니다."));
    }
}
