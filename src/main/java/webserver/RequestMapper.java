package webserver;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.exception.DuplicatedMappedRequestException;
import webserver.http.request.MappedRequest;

public class RequestMapper {
    private static final Map<MappedRequest, Method> requestMapper = new LinkedHashMap<>();
    private static final String DEFAULT_CONTROLLER_DIRECTORY = "application/controller";

    public static void scanRequestMappingAnnotatedMethod() {
        Reflections reflections = new Reflections(DEFAULT_CONTROLLER_DIRECTORY);
        Set<Class<?>> controllerAnnotatedClasses = reflections.getTypesAnnotatedWith(Controller.class);

        controllerAnnotatedClasses.stream()
            .map(scanRequestMappingAnnotatedMethods())
            .forEach(applyAnnotatedMethods());
    }

    private static Function<Class<?>, Set<Method>> scanRequestMappingAnnotatedMethods() {
        return aClass ->
            new Reflections(aClass.getName(), new MethodAnnotationsScanner())
                .getMethodsAnnotatedWith(RequestMapping.class);
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

    public static void add(MappedRequest mappedRequest, Method method) {
        if (requestMapper.containsKey(mappedRequest)) {
            throw new DuplicatedMappedRequestException(
                String.format("중복되는 Mapped Request가 있습니다 : %s와 %s", method, requestMapper.get(mappedRequest)));
        }
        requestMapper.put(mappedRequest, method);
    }

    public static Method get(MappedRequest mappedRequest) {
        return requestMapper.get(mappedRequest);
    }

    public static boolean isContain(MappedRequest mappedRequest) {
        return requestMapper.containsKey(mappedRequest);
    }

    protected static void clear() {
        requestMapper.clear();
    }
}
