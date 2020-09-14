package controller.handler;

import static webserver.HttpStatusCode.METHOD_NOT_ALLOW;
import static webserver.HttpStatusCode.NOT_FOUND;

import controller.ControllerAdvice;
import controller.UserController;
import controller.annotation.Controller;
import controller.annotation.RequestMapping;
import db.DataBase;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;
import webserver.utils.ResponseUtils;

public class ControllerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHandler.class);

    private final ControllerAdvice controllerAdvice;
    private final UserController userController;
    private final Map<Method, Object> controllerMethods;

    public ControllerHandler() throws NoSuchFieldException, IllegalAccessException {
        controllerAdvice = new ControllerAdvice();
        DataBase dataBase = new DataBase();
        UserService userService = new UserService(dataBase);
        this.userController = new UserController(controllerAdvice, userService);
        this.controllerMethods = makeControllerMethodsAndInstances();
    }

    private Map<Method, Object> makeControllerMethodsAndInstances()
        throws NoSuchFieldException, IllegalAccessException {
        Map<Object, Class<?>> controllerInstancesAndClasses = getControllerInstancesAndClasses();
        return getControllerMethodsAndInstances(controllerInstancesAndClasses);
    }

    private Map<Object, Class<?>> getControllerInstancesAndClasses()
        throws NoSuchFieldException, IllegalAccessException {
        Field[] declaredFields = ControllerHandler.class.getDeclaredFields();
        Map<Object, Class<?>> controllerFieldAndClass = new HashMap<>();
        for (Field declaredField : declaredFields) {
            Class<?> type = declaredField.getType();
            addControllerInstanceAndClass(controllerFieldAndClass, declaredField, type);
        }
        return controllerFieldAndClass;
    }

    private void addControllerInstanceAndClass(Map<Object, Class<?>> controllerFieldAndClass,
        Field declaredField, Class<?> type) throws NoSuchFieldException, IllegalAccessException {
        if (type.getDeclaredAnnotationsByType(Controller.class).length > 0) {
            controllerFieldAndClass.put(getInstanceByFieldName(declaredField.getName()), type);
            LOGGER.info("Controller : {}", type);
        }
    }

    private Map<Method, Object> getControllerMethodsAndInstances(
        Map<Object, Class<?>> controllerFieldAndClass) {
        Map<Method, Object> controllerFieldAndMethod = new HashMap<>();
        for (Object instance : controllerFieldAndClass.keySet()) {
            Class<?> type = controllerFieldAndClass.get(instance);
            Method[] methods = type.getMethods();
            for (Method method : methods) {
                if (method.getDeclaredAnnotationsByType(RequestMapping.class).length > 0) {
                    controllerFieldAndMethod.put(method, instance);
                    LOGGER.info("{} Method: {}", type.getSimpleName(), method);
                }
            }
        }
        return controllerFieldAndMethod;
    }

    private Object getInstanceByFieldName(String fieldName)
        throws NoSuchFieldException, IllegalAccessException {
        return this.getClass().getDeclaredField(fieldName).get(this);
    }

    public void handleAPI(OutputStream out, HttpRequest httpRequest) throws IOException {
        MatchInfo matchInfo = getMatchInstanceMethod(httpRequest);

        try (DataOutputStream dos = new DataOutputStream(out)) {
            HttpResponse httpResponse = makeHttpResponse(httpRequest, matchInfo);
            response(dos, httpResponse);
        }
    }

    private MatchInfo getMatchInstanceMethod(HttpRequest httpRequest) {
        Object matchedInstance = null;
        Method matchedMethod = null;
        int sameUrlPathCount = 0;

        for (Method method : controllerMethods.keySet()) {
            Object instance = controllerMethods.get(method);
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            if (annotation.urlPath().equals(httpRequest.getUrlPath())) {
                sameUrlPathCount++;
                if (annotation.method().isSame(httpRequest.getHttpMethod())) {
                    matchedInstance = instance;
                    matchedMethod = method;
                    break;
                }
            }
        }
        LOGGER.info("Instance: {}", matchedInstance);
        LOGGER.info("Method: {}", matchedMethod);
        LOGGER.info("sameUrlPathCount: {}", sameUrlPathCount);
        return new MatchInfo(matchedInstance, matchedMethod, sameUrlPathCount);
    }

    private HttpResponse makeHttpResponse(HttpRequest httpRequest, MatchInfo matchInfo) {
        if (matchInfo.isMatch()) {
            return executeMatchedMethod(httpRequest, matchInfo);
        }

        if (matchInfo.isNotMatch() && matchInfo.anyMatchUrlPath()) {
            return controllerAdvice.handleHttpStatusCode(httpRequest, METHOD_NOT_ALLOW);
        }

        return controllerAdvice.handleHttpStatusCode(httpRequest, NOT_FOUND);
    }

    private HttpResponse executeMatchedMethod(HttpRequest httpRequest, MatchInfo matchInfo) {
        try {
            return matchInfo.executeMethod(httpRequest);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return controllerAdvice.handleCauseException(httpRequest, e);
        }
    }

    private void response(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        ResponseUtils.response(dos, httpResponse);
        LOGGER.info("httpResponse: {}", httpResponse);
    }
}
