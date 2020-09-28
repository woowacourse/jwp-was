package jwp.was.controller.handler;

import java.lang.reflect.Method;
import java.util.Objects;

public class MatchedInfo {

    private static final int DEFAULT_COUNT = 0;

    private final Method method;
    private final int urlPathCount;

    public MatchedInfo(Method matchedMethod, int matchedUrlPathCount) {
        this.method = matchedMethod;
        this.urlPathCount = matchedUrlPathCount;
    }

    public boolean isMatch() {
        return Objects.nonNull(method);
    }

    public boolean isNotMatch() {
        return !isMatch();
    }

    public boolean anyMatchUrlPath() {
        return urlPathCount > DEFAULT_COUNT;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "MatchedInfo{" +
            "method=" + method +
            ", urlPathCount=" + urlPathCount +
            '}';
    }
}
