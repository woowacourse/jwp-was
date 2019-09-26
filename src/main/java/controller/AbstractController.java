package controller;

import webserver.Request;
import webserver.RequestMapping;
import webserver.Response;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public abstract class AbstractController implements Controller {

    protected Map<RequestMapping, Function<Request, Response>> methods;

    @Override
    public final Response service(Request request) {
        return methods.get(request.getRequestMapping()).apply(request);
    }

    @Override
    public final Set<RequestMapping> getMethodKeys() {
        return methods.keySet();
    }
}
