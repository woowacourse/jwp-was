package jwp.was.webapplicationserver.configure.controller;

import static jwp.was.util.HttpStatusCode.METHOD_NOT_ALLOW;
import static jwp.was.util.HttpStatusCode.NOT_FOUND;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jwp.was.dto.HttpRequest;
import jwp.was.dto.HttpResponse;
import jwp.was.webapplicationserver.configure.annotation.AnnotationChecker;
import jwp.was.webapplicationserver.configure.annotation.ResponseBody;
import jwp.was.webapplicationserver.configure.controller.info.MatchedInfo;
import jwp.was.webapplicationserver.configure.controller.info.ModelAndView;
import jwp.was.webapplicationserver.configure.controller.info.NotSupportMethodException;
import jwp.was.webapplicationserver.configure.controller.info.NotSupportUrlException;
import jwp.was.webapplicationserver.configure.maker.ConfigureMaker;
import jwp.was.webapplicationserver.configure.security.LoginFilter;
import jwp.was.webapplicationserver.configure.security.NeedLoginException;
import jwp.was.webapplicationserver.configure.security.WithLoginFilter;
import jwp.was.webapplicationserver.controller.GlobalExceptionHandler;
import jwp.was.webserver.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHandler.class);

    private final ConfigureMaker configureMaker = ConfigureMaker.getInstance();
    private final ControllerMapper controllerMapper = ControllerMapper.getInstance();
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
    private final WithLoginFilter withLoginFilter = WithLoginFilter.getInstance();
    private final LoginFilter loginFilter = LoginFilter.getInstance();

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
        try {
            withLoginFilter.validateLogin(httpRequest);
            return disposeRequest(httpRequest, matchedInfo);
        } catch (NeedLoginException e) {
            LOGGER.info("NeedLoginException: {}", e.getMessage());
            return withLoginFilter.getRedirectLoginPage(httpRequest);
        }
    }

    private HttpResponse disposeRequest(HttpRequest httpRequest, MatchedInfo matchedInfo) {
        if (loginFilter.isLoginRequest(httpRequest)) {
            return loginFilter.login(httpRequest);
        }
        return getHttpResponseAfterLogin(httpRequest, matchedInfo);
    }

    private HttpResponse getHttpResponseAfterLogin(HttpRequest httpRequest,
        MatchedInfo matchedInfo) {
        try {
            matchedInfo.validateMatch();
        } catch (NotSupportMethodException e) {
            return globalExceptionHandler.handleHttpStatusCode(httpRequest, METHOD_NOT_ALLOW);
        } catch (NotSupportUrlException e) {
            return globalExceptionHandler.handleHttpStatusCode(httpRequest, NOT_FOUND);
        }

        Method method = matchedInfo.getMethod();
        Object instance = configureMaker.getConfigure(method.getDeclaringClass());
        return getHttpResponse(httpRequest, method, instance);
    }

    private HttpResponse getHttpResponse(HttpRequest httpRequest, Method method, Object instance) {
        try {
            Object responseResult = method.invoke(instance, httpRequest);
            if (AnnotationChecker.includeAnnotation(instance, ResponseBody.class)) {
                return (HttpResponse) responseResult;
            }
            ModelAndView modelAndView = (ModelAndView) responseResult;
            return modelAndView.toHttpResponse(httpRequest);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return globalExceptionHandler.handleCauseException(httpRequest, e);
        }
    }

    private void response(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        ResponseUtils.response(dos, httpResponse);
        LOGGER.info("httpResponse: {}", httpResponse);
    }
}
