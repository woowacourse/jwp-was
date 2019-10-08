package webserver.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public final class MappedDestination {
    private static final Logger logger = LoggerFactory.getLogger(MappedDestination.class);

    private final Method controller;

    protected static Optional<MappedDestination> of(String className, String methodName) {
        try {
            return Optional.of(
                    new MappedDestination(
                            Class.forName(className).getMethod(methodName, HttpRequest.class)
                    )
            );
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            logger.error("No controller matches the name: {}.{}", className, methodName);
            return Optional.empty();
        }
    }

    protected MappedDestination(Method controller) {
        this.controller = controller;
    }

    public HttpResponse execute(HttpRequest req) {
        try {
            return (HttpResponse) this.controller.invoke(
                    Env.getInstanceOf(this.controller.getDeclaringClass()),
                    req
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
            return HttpResponse.INTERNAL_SERVER_ERROR;
        }
    }

    public String className() {
        return this.controller.getDeclaringClass().getName();
    }
    public String methodName() {
        return this.controller.getName();
    }
}