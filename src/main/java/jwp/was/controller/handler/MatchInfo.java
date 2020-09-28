package jwp.was.controller.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import jwp.was.webserver.dto.HttpRequest;
import jwp.was.webserver.dto.HttpResponse;

public class MatchInfo {

    private Object instance;
    private Method method;
    private int sameUrlPathCount;

    private MatchInfo(Object instance, Method method, int sameUrlPathCount) {
        this.instance = instance;
        this.method = method;
        this.sameUrlPathCount = sameUrlPathCount;
    }

    public static MatchInfo Default() {
        return new MatchInfo(null, null, 0);
    }

    public boolean anyMatchUrlPath() {
        return sameUrlPathCount > 0;
    }

    public boolean isNotMatch() {
        return Objects.isNull(method);
    }

    public boolean isMatch() {
        return Objects.nonNull(method);
    }

    public HttpResponse executeMethod(HttpRequest httpRequest)
        throws InvocationTargetException, IllegalAccessException {
        return (HttpResponse) method.invoke(instance, httpRequest);
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void increaseCount() {
        sameUrlPathCount++;
    }

    @Override
    public String toString() {
        return "MatchInfo{" +
            "instance=" + instance +
            ", method=" + method +
            ", sameUrlPathCount=" + sameUrlPathCount +
            '}';
    }
}
