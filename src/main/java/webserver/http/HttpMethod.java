package webserver.http;

import java.util.Arrays;

public enum HttpMethod {
    GET,
    POST;

    public static HttpMethod of(String methodName) {
        return Arrays.stream(values())
            .filter(httpMethod -> httpMethod.name().equalsIgnoreCase(methodName))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("허용되지 않는 Http Method 입니다."));
    }

	public boolean allowBody() {
		return this == POST;
	}
}
