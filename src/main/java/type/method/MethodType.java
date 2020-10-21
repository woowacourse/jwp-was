package type.method;

import java.util.Arrays;

import exception.NotSupportedMethodException;

public enum MethodType {

    GET,
    POST;

    public static MethodType find(final String method) {
        return Arrays.stream(MethodType.values())
                .filter(type -> type.name().equals(method))
                .findFirst()
                .orElseThrow(() -> new NotSupportedMethodException("지원하지 않는 Method 입니다."));
    }
}
