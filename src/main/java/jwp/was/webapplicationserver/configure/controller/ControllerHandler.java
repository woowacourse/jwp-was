package jwp.was.webapplicationserver.configure.controller;

import static jwp.was.webserver.HttpStatusCode.METHOD_NOT_ALLOW;
import static jwp.was.webserver.HttpStatusCode.NOT_FOUND;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jwp.was.webapplicationserver.configure.annotation.AnnotationChecker;
import jwp.was.webapplicationserver.configure.annotation.ResponseBody;
import jwp.was.webapplicationserver.configure.controller.info.MatchedInfo;
import jwp.was.webapplicationserver.configure.controller.info.ModelAndView;
import jwp.was.webapplicationserver.configure.maker.ConfigureMaker;
import jwp.was.webapplicationserver.configure.security.WithLoginConfigure;
import jwp.was.webapplicationserver.controller.GlobalExceptionHandler;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;
import jwp.was.webserver.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHandler.class);

    private final ConfigureMaker configureMaker = ConfigureMaker.getInstance();
    private final ControllerMapper controllerMapper = ControllerMapper.getInstance();
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
    private final WithLoginConfigure withLoginConfigure = WithLoginConfigure.getInstance();

    public ControllerHandler() {
    }

    public void handleAPI(OutputStream out, HttpRequest httpRequest) throws IOException {
        MatchedInfo matchedInfo = controllerMapper.getMatchMethod(httpRequest);

        try (DataOutputStream dos = new DataOutputStream(out)) {
            HttpResponse httpResponse = makeHttpResponse(httpRequest, matchedInfo);
            response(dos, httpResponse);
        }
    }

    private HttpResponse makeHttpResponse(HttpRequest httpRequest, MatchedInfo matchedInfo) {
        if (withLoginConfigure.verifyLogin(httpRequest)) {
            return getHttpResponseAfterLogin(httpRequest, matchedInfo);
        }
        return withLoginConfigure.getRedirectLoginPage(httpRequest);
    }

    private HttpResponse getHttpResponseAfterLogin(HttpRequest httpRequest,
        MatchedInfo matchedInfo) {
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
            return getHttpResponse(httpRequest, method, instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return globalExceptionHandler.handleCauseException(httpRequest, e);
        }
    }

    private HttpResponse getHttpResponse(HttpRequest httpRequest, Method method, Object instance)
        throws IllegalAccessException, InvocationTargetException {

        if (AnnotationChecker.includeAnnotation(instance, ResponseBody.class)) {
            return (HttpResponse) method.invoke(instance, httpRequest);
        }
        ModelAndView modelAndView = (ModelAndView) method.invoke(instance, httpRequest);
        return modelAndView.toHttpResponse(httpRequest);
    }

    private void response(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        ResponseUtils.response(dos, httpResponse);
        LOGGER.info("httpResponse: {}", httpResponse);
    }
}
