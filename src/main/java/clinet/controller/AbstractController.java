package clinet.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import web.controller.Controller;
import web.request.HttpMethod;
import web.request.HttpRequest;
import web.response.HttpResponse;

public abstract class AbstractController implements Controller {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractController.class);
	private static List<Controller> controllers = new ArrayList<>();

	static {
		controllers.add(new CreateUserController());
		controllers.add(new ResourceController());
	}

	public static Controller findController(String path) throws Exception {
		return controllers.stream()
			.filter(controller -> controller.canHandle(path))
			.findFirst()
			.orElseThrow(() -> new Exception("요청한 URI를 찾을 수 없습니다."));
	}

	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
		if (request.getMethod() == HttpMethod.GET) {
			doGet(request, response);
		}
		if (request.getMethod() == HttpMethod.POST) {
			doPost(request, response);
		}
	}

	@Override
	public boolean canHandle(String path) {
		return false;
	}

	protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
	}

	protected void doPost(HttpRequest request, HttpResponse response) {
	}
}
