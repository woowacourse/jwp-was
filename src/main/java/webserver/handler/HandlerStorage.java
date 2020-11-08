package webserver.handler;

import java.util.ArrayList;
import java.util.List;

public class HandlerStorage {
	private static final List<Handler> handlers = new ArrayList<>();

	static {
		handlers.add(new CreateUserHandler());
		handlers.add(new StaticResourceHandler());
		handlers.add(new LoginHandler());
	}

	public static Handler findHandler(String url) {
		return handlers.stream()
			.filter(handler -> handler.canHandle(url))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 요청입니다."));
	}
}
