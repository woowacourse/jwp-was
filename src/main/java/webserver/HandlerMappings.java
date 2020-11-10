package webserver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.exception.HandlerNotFoundException;
import webserver.http.request.HttpRequest;

public class HandlerMappings {
    private static final Logger logger = LoggerFactory.getLogger(HandlerMappings.class);

    private final List<HandlerMapping> handlers;

    public HandlerMappings(List<HandlerMapping> handlers) {
        this.handlers = handlers;
    }

    public HandlerMapping findHandler(HttpRequest request) {
        for (HandlerMapping handler : handlers) {
            System.out.println(handler + "핸들러를 체크한다");
            if (handler.isSupport(request)) {
                return handler;
            }
        }
        logger.error("요청을 처리하는 핸들러가 존재하지 않습니다. : " + request.getPath());
        throw new HandlerNotFoundException(request.getPath());
    }
}
