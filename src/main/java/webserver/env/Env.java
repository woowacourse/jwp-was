package webserver.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class Env {
    private static final Logger logger = LoggerFactory.getLogger(Env.class);

    private static final Reflections REFLECTIONS = new Reflections();
    private static final Router ROUTER = new Router(new RouterConfigAnnotation());
    private static final SessionStorage SESSION_STORAGE = new SessionStorage();
    private static final Map<Class<?>, Object> SINGLETONS = new HashMap<Class<?>, Object>() {{
        REFLECTIONS.getAllClassesAnnotatedX(Singleton.class).forEach(clazz -> {
            try {
                this.put(clazz, clazz.getDeclaredConstructor().newInstance());
            } catch (
                    InstantiationException | InvocationTargetException
                            | NoSuchMethodException | IllegalAccessException e
            ) {
                logger.error(e.getMessage());
            }
        });
    }};

    public static void init() {}

    public static Reflections reflections() {
        return REFLECTIONS;
    }

    public static Router router() {
        return ROUTER;
    }

    public static SessionStorage sessionStorage() {
        return SESSION_STORAGE;
    }

    public static Object getInstanceOf(Class<?> clazz) {
        return SINGLETONS.get(clazz);
    }
}