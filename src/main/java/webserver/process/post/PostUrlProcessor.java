package webserver.process.post;

import static webserver.http.response.HttpResponseHeaderName.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import model.User;
import webserver.exception.NotExistUrlException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseBody;
import webserver.http.response.HttpResponseHeaderName;
import webserver.http.response.HttpResponseHeaders;
import webserver.http.response.HttpStatusLine;
import webserver.http.response.StatusCode;
import webserver.process.get.UserParser;

public class PostUrlProcessor implements Function<HttpRequest, HttpResponse> {

	@Override
	public HttpResponse apply(HttpRequest httpRequest) {
		if (httpRequest.isSameUrl("/user/create")) {
			User user = UserParser.parseUser(httpRequest.getHttpBody());

			HttpStatusLine httpStatusLine = new HttpStatusLine(StatusCode.FOUND);
			String body = String.format("create user : userId=%s", user.getUserId());
			Map<HttpResponseHeaderName, String> headers = new HashMap<>();
			headers.put(LOCATION, "http://localhost:8080/index.html");
			HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders(headers);

			HttpResponse httpResponse = new HttpResponse(httpStatusLine, httpResponseHeaders,
				new HttpResponseBody(body));
			return httpResponse;
		}
		throw new NotExistUrlException("url : " + httpRequest.getRequestTarget());
	}
}
