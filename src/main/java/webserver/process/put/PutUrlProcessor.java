package webserver.process.put;

import java.util.function.Function;

import webserver.exception.NotExistUrlException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class PutUrlProcessor implements Function<HttpRequest, HttpResponse> {

	@Override
	public HttpResponse apply(HttpRequest httpRequest) {
		throw new NotExistUrlException();
	}
}
