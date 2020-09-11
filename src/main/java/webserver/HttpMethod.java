package webserver;

import java.util.Arrays;
import java.util.function.Function;

import webserver.process.DeleteUrlProcessor;
import webserver.process.get.GetUrlProcessor;
import webserver.process.PatchUrlProcessor;
import webserver.process.PostUrlProcessor;
import webserver.process.PutUrlProcessor;

public enum HttpMethod {

	GET(new GetUrlProcessor()),
	POST(new PostUrlProcessor()),
	PUT(new PutUrlProcessor()),
	DELETE(new DeleteUrlProcessor()),
	PATCH(new PatchUrlProcessor());

	private final Function<String, byte[]> urlProcessor;

	HttpMethod(Function<String, byte[]> urlProcessor) {
		this.urlProcessor = urlProcessor;
	}

	public static HttpMethod of(String value) {
		return Arrays.stream(values())
			.filter(method -> method.name().equals(value))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(String.format("%s는 HttpMethod에 포함되지 않습니다..", value)));
	}

	public byte[] processByUrl(String url) {
		return this.urlProcessor.apply(url);
	}
}