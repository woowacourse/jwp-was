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
    private static final Map<MappedRequest, RequestInvoker> requestMapper = new HashMap<>();

    private Reflections reflections;

    public RequestMapper(String scanPackage) {
        reflections = new Reflections(scanPackage);
    }

    public void scanRequestMappingAnnotatedMethod() {
        Set<Class<?>> controllerAnnotatedClasses = reflections.getTypesAnnotatedWith(Controller.class);

        controllerAnnotatedClasses.stream()
                .map(scanRequestMappingAnnotatedMethods())
                .forEach(applyAnnotatedMethods());
    }

    private Consumer<Set<Method>> applyAnnotatedMethods() {
        return classes -> classes.forEach(addAnnotatedMethodToRequestMapper());
    }

    private Consumer<Method> addAnnotatedMethodToRequestMapper() {
        return annotatedMethod -> {
            try {
                RequestMapping annotation = annotatedMethod.getAnnotation(RequestMapping.class);
                MappedRequest mappedRequest = new MappedRequest(annotation.method(), annotation.path());
                RequestInvoker requestInvoker = new RequestInvoker(annotatedMethod.getDeclaringClass().newInstance(),
                        annotatedMethod);
                add(mappedRequest, requestInvoker);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        };
    }

    private void add(MappedRequest mappedRequest, RequestInvoker requestInvoker) {
        if (requestMapper.containsKey(mappedRequest)) {
            throw new DuplicatedMappedRequestException("중복되는 값이 있습니다.");
        }

        requestMapper.put(mappedRequest, requestInvoker);
    }

    private Function<Class<?>, Set<Method>> scanRequestMappingAnnotatedMethods() {
        return aClass ->
                new Reflections(aClass.getName(), new MethodAnnotationsScanner())
                        .getMethodsAnnotatedWith(RequestMapping.class);
    }

    public RequestInvoker get(MappedRequest mappedRequest) {
        return requestMapper.get(mappedRequest);
    }
}
