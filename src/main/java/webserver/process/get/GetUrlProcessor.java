package webserver.process.get;

import static webserver.http.response.HttpResponseHeaderName.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import utils.FileIoUtils;
import webserver.exception.NotExistUrlException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseBody;
import webserver.http.response.HttpResponseHeaderName;
import webserver.http.response.HttpResponseHeaders;
import webserver.http.response.HttpStatusLine;
import webserver.http.response.StatusCode;
import webserver.http.response.content.type.ContentType;

public class GetUrlProcessor implements Function<HttpRequest, HttpResponse> {

	@Override
	public HttpResponse apply(HttpRequest httpRequest) {
		if (httpRequest.isSameUrl("/index.html")) {
			try {
				String body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
				return compose200HttpResponse(body, ContentType.HTML);
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if (httpRequest.isSameUrl("/user/form.html")) {
			try {
				String body = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
				return compose200HttpResponse(body, ContentType.HTML);
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if (httpRequest.isSameUrl("/css/styles.css")) {
			try {
				String body = FileIoUtils.loadFileFromClasspath("./static/css/styles.css");
				return compose200HttpResponse(body, ContentType.CSS);
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if (httpRequest.isSameUrl("/css/bootstrap.min.css")) {
			try {
				String body = FileIoUtils.loadFileFromClasspath("./static/css/bootstrap.min.css");
				return compose200HttpResponse(body, ContentType.CSS);
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		throw new NotExistUrlException("url : " + httpRequest.getRequestTarget());
	}

	private HttpResponse compose200HttpResponse(String body, ContentType contentType) {
		HttpStatusLine httpStatusLine = new HttpStatusLine(StatusCode.OK);
		Map<HttpResponseHeaderName, String> headers = new HashMap<>();
		headers.put(CONTENT_TYPE, contentType.getValue());
		headers.put(CONTENT_LENGTH, String.valueOf(body.length()));
		HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders(headers);

		return new HttpResponse(httpStatusLine, httpResponseHeaders, new HttpResponseBody(body));
	}
}
