package jwp.was.webapplicationserver.configure.controller.info;

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

    public Method getMethod() {
        return method;
    }

    public void validateMatch() {
        if (Objects.nonNull(method)) {
            return;
        }
        if (urlPathCount > DEFAULT_COUNT) {
            throw new NotSupportMethodException();
        }
        throw new NotSupportUrlException();
    }

    @Override
    public String toString() {
        return "MatchedInfo{" +
            "method=" + method +
            ", urlPathCount=" + urlPathCount +
            '}';
    }
}
