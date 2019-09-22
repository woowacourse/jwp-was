package http.method;

public enum HttpMethod {
	GET("GET"), POST("POST");

	private String method;

	HttpMethod(final String method) {
		this.method = method;
	}

	public boolean isSameMethod(String method) {
		return this.method.equals(method);
	}
}
