package jwp.was.webapplicationserver.controller.handler;

import static jwp.was.webserver.HttpStatusCode.METHOD_NOT_ALLOW;
import static jwp.was.webserver.HttpStatusCode.NOT_FOUND;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jwp.was.webapplicationserver.configure.ConfigureMaker;
import jwp.was.webapplicationserver.configure.HttpInfoMethodMapper;
import jwp.was.webapplicationserver.controller.GlobalExceptionHandler;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;
import jwp.was.webserver.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHandler.class);

    private final ConfigureMaker configureMaker = ConfigureMaker.getInstance();
    private final HttpInfoMethodMapper httpInfoMethodMapper = HttpInfoMethodMapper.getInstance();
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    public ControllerHandler() {
    }

    public void handleAPI(OutputStream out, HttpRequest httpRequest) throws IOException {
        MatchedInfo matchedInfo = httpInfoMethodMapper.getMatchMethod(httpRequest);

        try (DataOutputStream dos = new DataOutputStream(out)) {
            HttpResponse httpResponse = makeHttpResponse(httpRequest, matchedInfo);
            response(dos, httpResponse);
        }
    }

    private HttpResponse makeHttpResponse(HttpRequest httpRequest, MatchedInfo matchedInfo) {
        if (matchedInfo.isMatch()) {
            return executeMatchedMethod(httpRequest, matchedInfo);
        }

        if (matchedInfo.isNotMatch() && matchedInfo.anyMatchUrlPath()) {
            return globalExceptionHandler.handleHttpStatusCode(httpRequest, METHOD_NOT_ALLOW);
        }

        return globalExceptionHandler.handleHttpStatusCode(httpRequest, NOT_FOUND);
    }

    private HttpResponse executeMatchedMethod(HttpRequest httpRequest, MatchedInfo matchedInfo) {
        try {
            Method method = matchedInfo.getMethod();
            Object instance = configureMaker.getConfigure(method.getDeclaringClass());
            return (HttpResponse) method.invoke(instance, httpRequest);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return globalExceptionHandler.handleCauseException(httpRequest, e);
        }
    }

    private void response(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        ResponseUtils.response(dos, httpResponse);
        LOGGER.info("httpResponse: {}", httpResponse);
    }
}
