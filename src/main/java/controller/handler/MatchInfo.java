package controller.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;

public class MatchInfo {

    private final Object instance;
    private final Method method;
    private final int sameUrlPathCount;

    public MatchInfo(Object instance, Method method, int sameUrlPathCount) {
        validate(instance, method);
        this.instance = instance;
        this.method = method;
        this.sameUrlPathCount = sameUrlPathCount;
    }

    private void validate(Object instance, Method method) {
        if (Objects.isNull(instance) != Objects.isNull(method)) {
            throw new IllegalArgumentException("인스턴스와 메서드가 제대로 매칭되지 않았습니다.");
        }
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
}
