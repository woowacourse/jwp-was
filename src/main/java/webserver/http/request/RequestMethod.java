package webserver.http.request;

import java.util.Arrays;

public enum RequestMethod {
    GET,
    POST;

    public static RequestMethod of(String methodName) {
        return Arrays.stream(values())
            .filter(requestMethod -> requestMethod.name().equalsIgnoreCase(methodName))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("허용되지 않는 Http Method 입니다."));
    }

	public boolean allowBody() {
		return this == POST;
	}
}
