package webserver.http.request;

import webserver.process.HttpProcessor;

public class HttpRequestStartLine {

	private final String httpMethod;
	private final String requestTarget;
	private final String httpVersion;

	public HttpRequestStartLine(String httpMethod, String requestTarget, String httpVersion) {
		this.httpMethod = httpMethod;
		this.requestTarget = requestTarget;
		this.httpVersion = httpVersion;
	}

	public boolean containsInUrl(String url) {
		return getRequestTarget().contains(url);
	}

	public boolean hasNotBody() {
		return !HttpProcessor.POST.isMatchMethod(getHttpMethod());
	}

	public boolean isSameUrl(String url) {
		return getRequestTarget().equals(url);
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getRequestTarget() {
		return requestTarget;
	}

	public String getHttpVersion() {
		return httpVersion;
	}
}
