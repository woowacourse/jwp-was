package webserver.process.patch;

import java.util.function.Function;

import webserver.exception.NotExistUrlException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class PatchUrlProcessor implements Function<HttpRequest, HttpResponse> {

	@Override
	public HttpResponse apply(HttpRequest httpRequest) {
		throw new NotExistUrlException("url : " + httpRequest.getRequestTarget());

	}
}
