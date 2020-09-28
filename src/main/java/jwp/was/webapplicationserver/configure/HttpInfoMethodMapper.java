package jwp.was.webapplicationserver.configure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import jwp.was.webapplicationserver.configure.annotation.Controller;
import jwp.was.webapplicationserver.configure.annotation.RequestMapping;
import jwp.was.webapplicationserver.controller.handler.HttpInfo;
import jwp.was.webapplicationserver.controller.handler.MatchedInfo;
import jwp.was.webserver.dto.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpInfoMethodMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpInfoMethodMapper.class);
    private static final HttpInfoMethodMapper INSTANCE = new HttpInfoMethodMapper();

    private final Map<HttpInfo, Method> httpInfoMethodMapper;

    private HttpInfoMethodMapper() {
        Map<HttpInfo, Method> httpInfoMethodMapper = new HashMap<>();
        Set<Object> controllerInstances = ConfigureMaker.getInstance()
            .getConfiguresWithAnnotation(Controller.class);

        for (Object controllerInstance : controllerInstances) {
            Method[] methods = controllerInstance.getClass().getDeclaredMethods();
            putMethods(httpInfoMethodMapper, methods);
        }
        this.httpInfoMethodMapper = httpInfoMethodMapper;
    }

    public static HttpInfoMethodMapper getInstance() {
        return INSTANCE;
    }

    private void putMethods(Map<HttpInfo, Method> httpInfoMethodMapper, Method[] methods) {
        for (Method method : methods) {
            putMethod(httpInfoMethodMapper, method);
        }
    }

    private void putMethod(Map<HttpInfo, Method> httpInfoMethodMapper, Method method) {
        if (!method.isAnnotationPresent(RequestMapping.class)) {
            return;
        }
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        HttpInfo httpInfo = HttpInfo.of(annotation.method(), annotation.urlPath());
        validateDuplicateHttpInfo(httpInfoMethodMapper, httpInfo);
        httpInfoMethodMapper.put(httpInfo, method);
    }

    private void validateDuplicateHttpInfo(Map<HttpInfo, Method> httpInfoMethodMapper,
        HttpInfo httpInfo) {
        if (httpInfoMethodMapper.get(httpInfo) != null) {
            LOGGER.error("중복된 Request Mapping 발생! : {}", httpInfo);
            System.exit(0);
        }
    }

    public MatchedInfo getMatchMethod(HttpRequest httpRequest) {
        String urlPath = httpRequest.getUrlPath();
        int matchedUrlPathCount = getMatchedUrlPathCount(urlPath);

        HttpInfo httpInfo = HttpInfo.of(httpRequest.getHttpMethod(), urlPath);
        Method matchedMethod = httpInfoMethodMapper.get(httpInfo);

        return new MatchedInfo(matchedMethod, matchedUrlPathCount);
    }

    private int getMatchedUrlPathCount(String urlPath) {
        return (int) httpInfoMethodMapper.keySet().stream()
            .filter(httpInfo -> httpInfo.isSamePath(urlPath))
            .count();
    }
}
