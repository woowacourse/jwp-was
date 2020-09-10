package webserver;

import java.util.Arrays;

public enum HttpMethod {
	GET, POST, PUT, DELETE, PATCH;

	public static HttpMethod of(String value) {
		return Arrays.stream(values())
			.filter(method -> method.name().equals(value))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(String.format("%s는 HttpMethod에 포함되지 않습니다..", value)));
	}
}