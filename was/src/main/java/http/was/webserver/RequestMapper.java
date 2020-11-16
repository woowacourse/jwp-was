package http.was.webserver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
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
    private static final Set<Object> controllerInstances = new HashSet<>();

    private Reflections reflections;

    public RequestMapper(String scanPackage) {
        reflections = new Reflections(scanPackage);
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> aClass) {
        return (T)controllerInstances.stream()
                .filter(controllerInstance -> controllerInstance.getClass().equals(aClass))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 인스턴스가 업습니다."));
    }

    public void scanRequestMappingAnnotatedMethod() {
        Set<Class<?>> controllerAnnotatedClasses = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> cls : controllerAnnotatedClasses) {
            for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
                try {
                    controllerInstances.add(constructor.newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        controllerAnnotatedClasses.stream()
                .map(scanRequestMappingAnnotatedMethods())
                .forEach(applyAnnotatedMethods());
    }

    private Consumer<Set<Method>> applyAnnotatedMethods() {
        return classes -> classes.forEach(addAnnotatedMethodToRequestMapper());
    }

    private Consumer<Method> addAnnotatedMethodToRequestMapper() {
        return annotatedMethod -> {
            RequestMapping annotation = annotatedMethod.getAnnotation(RequestMapping.class);
            MappedRequest mappedRequest = new MappedRequest(annotation.method(), annotation.path());
            add(mappedRequest, annotatedMethod);
        };
    }

    private void add(MappedRequest mappedRequest, Method method) {
        if (requestMapper.containsKey(mappedRequest)) {
            throw new DuplicatedMappedRequestException("중복되는 값이 있습니다.");
        }

        requestMapper.put(mappedRequest, method);
    }

    private Function<Class<?>, Set<Method>> scanRequestMappingAnnotatedMethods() {
        return aClass ->
                new Reflections(aClass.getName(), new MethodAnnotationsScanner())
                        .getMethodsAnnotatedWith(RequestMapping.class);
    }

    public Method get(MappedRequest mappedRequest) {
        return requestMapper.get(mappedRequest);
    }
}
