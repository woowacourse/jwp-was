package webserver.http.request;

import java.util.Map;

import webserver.process.HttpProcessor;

public class HttpRequestHttpHeaders {

	private final Map<String, String> httpHeaders;

	public HttpRequestHttpHeaders(Map<String, String> httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	public boolean isSameUrl(String url) {
		return getUrl().equals(url);
	}

	public boolean containsInUrl(String url) {
		return getUrl().contains(url);
	}

	public boolean hasNotBody() {
		return !HttpProcessor.POST.isMatchMethod(getHttpMethod());
	}

	public int size() {
		return httpHeaders.size();
	}

	public String getHttpMethod() {
		return httpHeaders.get(HttpRequestHeaderName.Method.name());
	}

	public String getUrl() {
		return httpHeaders.get(HttpRequestHeaderName.RequestUrl.name());
	}

	public String getByHeaderName(String headerName) {
		return httpHeaders.get(headerName);
	}
}
