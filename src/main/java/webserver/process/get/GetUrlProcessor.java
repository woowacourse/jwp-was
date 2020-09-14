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

public class GetUrlProcessor implements Function<HttpRequest, HttpResponse> {

	@Override
	public HttpResponse apply(HttpRequest httpRequest) {
		if (httpRequest.isSameUrl("/index.html")) {
			try {
				HttpStatusLine httpStatusLine = new HttpStatusLine(StatusCode.OK);
				String body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
				Map<HttpResponseHeaderName, String> headers = new HashMap<>();
				headers.put(CONTENT_TYPE, "text/html;charset=utf-8");
				headers.put(CONTENT_LENGTH, String.valueOf(body.length()));
				HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders(headers);

				return new HttpResponse(httpStatusLine, httpResponseHeaders, new HttpResponseBody(body));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if (httpRequest.isSameUrl("/user/form.html")) {
			try {
				HttpStatusLine httpStatusLine = new HttpStatusLine(StatusCode.OK);
				String body = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
				Map<HttpResponseHeaderName, String> headers = new HashMap<>();
				headers.put(CONTENT_TYPE, "text/html;charset=utf-8");
				headers.put(CONTENT_LENGTH, String.valueOf(body.length()));
				HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders(headers);

				return new HttpResponse(httpStatusLine, httpResponseHeaders, new HttpResponseBody(body));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		throw new NotExistUrlException();
	}
}
