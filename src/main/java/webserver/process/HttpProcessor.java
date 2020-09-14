package webserver.process;

import java.util.Arrays;
import java.util.function.Function;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.process.delete.DeleteUrlProcessor;
import webserver.process.get.GetUrlProcessor;
import webserver.process.patch.PatchUrlProcessor;
import webserver.process.post.PostUrlProcessor;
import webserver.process.put.PutUrlProcessor;

public enum HttpProcessor {

	GET(new GetUrlProcessor()),
	POST(new PostUrlProcessor()),
	PUT(new PutUrlProcessor()),
	DELETE(new DeleteUrlProcessor()),
	PATCH(new PatchUrlProcessor());

	private final Function<HttpRequest, HttpResponse> urlProcessor;

	HttpProcessor(Function<HttpRequest, HttpResponse> urlProcessor) {
		this.urlProcessor = urlProcessor;
	}

	public static HttpResponse process(HttpRequest httpRequest) {
		HttpProcessor httpProcessor = Arrays.stream(values())
			.filter(method -> method.isMatchMethod(httpRequest.getHttpMethod()))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(
				String.format("%s는 HttpMethod에 포함되지 않습니다..", httpRequest.getHttpMethod())));

		return httpProcessor.urlProcessor.apply(httpRequest);
	}

	public boolean isMatchMethod(String httpMethod) {
		return this.name().equals(httpMethod);
	}
}