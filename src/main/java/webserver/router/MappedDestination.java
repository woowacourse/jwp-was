package webserver.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class MappedDestination {
    private static final Logger logger = LoggerFactory.getLogger(MappedDestination.class);

    private final Method controller;
    private final Map<String, String> pathVars;

    protected static Optional<MappedDestination> of(String className, String methodName, Map<String, String> pathVars) {
        try {
            if (pathVars.isEmpty()) {
                return Optional.of(
                        new MappedDestination(
                                Class.forName(className).getMethod(methodName, HttpRequest.class),
                                pathVars
                        )
                );
            }
            return Optional.of(
                    new MappedDestination(
                            Class.forName(className).getMethod(methodName, HttpRequest.class, Map.class),
                            pathVars
                    )
            );
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            logger.error("No controller matches the name: " + className + "." + methodName);
            return Optional.empty();
        }
    }

    protected MappedDestination(Method controller, Map<String, String> pathVars) {
        this.controller = controller;
        this.pathVars = Collections.unmodifiableMap(pathVars);
    }

    public HttpResponse execute(HttpRequest req) {
        try {
            return (HttpResponse) (
                    (this.pathVars.isEmpty())
                            ? this.controller.invoke(null, req)
                            : this.controller.invoke(null, req, pathVars)
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
            return HttpResponse.INTERNAL_SERVER_ERROR;
        }
    }
}