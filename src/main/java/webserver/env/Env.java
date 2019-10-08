package webserver.env;

import java.util.HashMap;
import java.util.Map;

public final class Env {
    private static final Reflections reflections = new Reflections();
    private static final Router router = new Router(new RouterConfigAnnotation());
    private static final SessionStorage sessionStorage = new SessionStorage();
    private static final Map<Class<?>, Object> singletons = new HashMap<Class<?>, Object>() {{
        reflections.getAllClassesAnnotatedX(Singleton.class).forEach(x -> {
            try {
                put(x, x.getDeclaredConstructor().newInstance());
            } catch (Exception e) {}
        });
    }};

    public static Reflections reflections() {
        return reflections;
    }

    public static Router router() {
        return router;
    }

    public static SessionStorage sessionStorage() {
        return sessionStorage;
    }

    public static Object getInstanceOf(Class<?> clazz) {
        return singletons.get(clazz);
    }
}