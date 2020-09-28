package jwp.was.controller.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import jwp.was.configure.maker.ConfigureMaker;
import jwp.was.controller.annotation.RequestMapping;
import jwp.was.webserver.dto.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerMethodMappers {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerMethodMappers.class);

    private final static Map<Method, Object> controllerMethodMappers;

    static {
        controllerMethodMappers = getControllerMethodMappers(
            ConfigureMaker.getInstance().getControllerInstances());
    }

    private static Map<Method, Object> getControllerMethodMappers(Set<Object> controllers) {
        Map<Method, Object> controllerMethodMapper = new HashMap<>();
        for (Object controller : controllers) {
            putControllerMethod(controllerMethodMapper, controller);
        }
        return controllerMethodMapper;
    }

    private static void putControllerMethod(Map<Method, Object> controllerMethodMapper,
        Object controller) {
        Class<?> type = controller.getClass();
        Method[] methods = type.getMethods();

        for (Method method : methods) {
            putControllerMethod(controllerMethodMapper, controller, method);
        }
    }

    private static void putControllerMethod(Map<Method, Object> controllerMethodMapper,
        Object controller,
        Method method) {

        boolean hasRequestMappingAnnotation
            = method.getDeclaredAnnotationsByType(RequestMapping.class).length > 0;

        if (hasRequestMappingAnnotation) {
            controllerMethodMapper.put(method, controller);
            LOGGER.info("{} Method: {}", controller.getClass().getSimpleName(), method);
        }
    }

    MatchInfo getMatchInstanceMethod(HttpRequest httpRequest) {
        MatchInfo matchedInfo = MatchInfo.Default();

        for (Method method : controllerMethodMappers.keySet()) {
            if (matchedInfo.isMatch()) {
                break;
            }
            setMatchedInfo(httpRequest, matchedInfo, method);
        }
        LOGGER.info("matchInfo: {}", matchedInfo.toString());
        return matchedInfo;
    }

    private void setMatchedInfo(HttpRequest httpRequest, MatchInfo matchedInfo, Method method) {
        Object instance = controllerMethodMappers.get(method);
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);

        boolean sameUrlPath = annotation.urlPath().equals(httpRequest.getUrlPath());
        increaseCount(matchedInfo, sameUrlPath);

        boolean sameHttpMethod = annotation.method().isSame(httpRequest.getHttpMethod());
        if (sameUrlPath && sameHttpMethod) {
            matchedInfo.setInstance(instance);
            matchedInfo.setMethod(method);
        }
    }

    private void increaseCount(MatchInfo matchedInfo, boolean sameUrlPath) {
        if (sameUrlPath) {
            matchedInfo.increaseCount();
        }
    }
}
