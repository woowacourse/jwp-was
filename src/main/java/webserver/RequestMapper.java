package webserver;

import java.lang.reflect.InvocationTargetException;
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
    private static final String ADD_METHOD_NAME = "add";

    public static void scanRequestMappingAnnotatedMethod() throws NoSuchMethodException {
        Reflections reflections = new Reflections(DEFAULT_CONTROLLER_DIRECTORY);
        Set<Class<?>> controllerAnnotatedClasses = reflections.getTypesAnnotatedWith(Controller.class);

        Method addToRequestMapper = RequestMapper.class.getMethod(ADD_METHOD_NAME, MappedRequest.class, Method.class);

        controllerAnnotatedClasses.stream()
            .map(findRequestMappingAnnotatedMethods())
            .forEach(applyAnnotatedMethodsTo(addToRequestMapper));
    }

    private static Function<Class<?>, Set<Method>> findRequestMappingAnnotatedMethods() {
        return aClass ->
            new Reflections(aClass.getName(), new MethodAnnotationsScanner())
                .getMethodsAnnotatedWith(RequestMapping.class);
    }

    private static Consumer<Set<Method>> applyAnnotatedMethodsTo(Method addToRequestMapper) {
        return classes -> {
            classes.forEach(addAnnotatedMethodToRequestMapper(addToRequestMapper));
        };
    }

    private static Consumer<Method> addAnnotatedMethodToRequestMapper(Method addToRequestMapper) {
        return annotatedMethod -> {
            RequestMapping annotation = annotatedMethod.getAnnotation(RequestMapping.class);
            MappedRequest mappedRequest = new MappedRequest(annotation.method(), annotation.path());
            try {
                addToRequestMapper.invoke(null, mappedRequest, annotatedMethod);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
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
        return null != get(mappedRequest);
    }

    protected static void clear() {
        requestMapper.clear();
    }
}
