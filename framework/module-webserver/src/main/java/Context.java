import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import annotations.Controller;
import annotations.RequestMapping;
import domain.HttpRequest;
import domain.HttpResponse;

public class Context {
    private static final String DEFAULT_CONTROLLER_DIRECTORY = "controller";
    private static final Context instance = new Context();

    private final Map<String, Method> requestMapper;
    private final List<Object> beans;

    private Context() {
        requestMapper = new HashMap<>();
        beans = instantiate();
        initialize();
    }

    public static Context getInstance() {
        return instance;
    }

    private List<Object> instantiate() {
        Reflections reflections = new Reflections(DEFAULT_CONTROLLER_DIRECTORY);
        Set<Class<?>> controllerAnnotatedClasses = reflections.getTypesAnnotatedWith(Controller.class);

        return controllerAnnotatedClasses.stream()
            .map(this::instantiateBean)
            .collect(Collectors.toList());
    }

    private Object instantiateBean(Class<?> aClass) {
        try {
            return aClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initialize() {
        beans.stream()
            .map(getAnnotatedMethods())
            .forEach(it -> it.forEach(addMethodToRequestMapper()));
    }

    private Function<Object, Set<Method>> getAnnotatedMethods() {
        return it -> new Reflections(it.getClass(), new MethodAnnotationsScanner()).getMethodsAnnotatedWith(
            RequestMapping.class);
    }

    private Consumer<Method> addMethodToRequestMapper() {
        return method -> {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            requestMapper.put(annotation.path(), method);
        };
    }

    public void invoke(HttpRequest httpRequest, HttpResponse httpResponse) throws
        InvocationTargetException, IllegalAccessException {
        String path = httpRequest.getPath();
        Method method = getAllocatedMethod(path);
        Object bean = getInstance(method);
        method.invoke(bean, httpRequest, httpResponse);
    }

    private Method getAllocatedMethod(String path) {
        Method method = requestMapper.get(path);
        if (Objects.isNull(method)) {
            return requestMapper.get("file");
        }
        return method;
    }

    private Object getInstance(Method method) {
        return beans.stream()
            .filter(t -> Arrays.asList(t.getClass().getMethods()).contains(method))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
