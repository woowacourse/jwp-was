package http.was.webserver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import http.was.controller.MappedRequest;
import http.was.controller.annotation.Controller;
import http.was.controller.annotation.RequestMapping;
import http.was.exception.DuplicatedMappedRequestException;

public class RequestMapper {
    private static final Map<MappedRequest, Method> requestMapper = new HashMap<>();
    private static final String DEFAULT_PACKAGE_SCAN = "user/service/controller";

    public static void scanRequestMappingAnnotatedMethod() {
        Reflections reflections = new Reflections(DEFAULT_PACKAGE_SCAN);
        Set<Class<?>> controllerAnnotatedClasses = reflections.getTypesAnnotatedWith(Controller.class);

        controllerAnnotatedClasses.stream()
                .map(scanRequestMappingAnnotatedMethods())
                .forEach(applyAnnotatedMethods());
    }

    private static Consumer<Set<Method>> applyAnnotatedMethods() {
        return classes -> classes.forEach(addAnnotatedMethodToRequestMapper());
    }

    private static Consumer<Method> addAnnotatedMethodToRequestMapper() {
        return annotatedMethod -> {
            RequestMapping annotation = annotatedMethod.getAnnotation(RequestMapping.class);
            MappedRequest mappedRequest = new MappedRequest(annotation.method(), annotation.path());
            add(mappedRequest, annotatedMethod);
        };
    }

    private static void add(MappedRequest mappedRequest, Method method) {
        if (requestMapper.containsKey(mappedRequest)) {
            throw new DuplicatedMappedRequestException("중복되는 값이 있습니다.");
        }

        requestMapper.put(mappedRequest, method);
    }

    private static Function<Class<?>, Set<Method>> scanRequestMappingAnnotatedMethods() {
        return aClass ->
                new Reflections(aClass.getName(), new MethodAnnotationsScanner())
                        .getMethodsAnnotatedWith(RequestMapping.class);
    }

    public static Method get(MappedRequest mappedRequest) {
        return requestMapper.get(mappedRequest);
    }
}
