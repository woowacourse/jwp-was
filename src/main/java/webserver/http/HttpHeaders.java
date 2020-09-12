package webserver.http;

import java.util.Map;

import webserver.process.HttpProcessor;

public class HttpHeaders {

	private final Map<String, String> httpHeaders;

	public HttpHeaders(Map<String, String> httpHeaders) {
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
		return httpHeaders.get(HeaderName.Method.name());
	}

	public String getUrl() {
		return httpHeaders.get(HeaderName.RequestUrl.name());
	}

	public String getByHeaderName(String headerName) {
		return httpHeaders.get(headerName);
	}
}
