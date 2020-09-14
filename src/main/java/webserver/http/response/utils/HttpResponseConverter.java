package webserver.http.response.utils;

import webserver.http.response.HttpResponse;

public class HttpResponseConverter {

	public static byte[] convert(HttpResponse httpResponse) {
		StringBuilder stringBuilder = new StringBuilder();

		convertStatusLine(httpResponse, stringBuilder);

		convertHeaders(httpResponse, stringBuilder);

		stringBuilder.append("\n");

		stringBuilder.append(httpResponse.getHttpRequestBody().getBody());

		return stringBuilder.toString().getBytes();
	}

	private static void convertHeaders(HttpResponse httpResponse, StringBuilder stringBuilder) {
		httpResponse.getHttpResponseHeaders().getHttpHeaders().entrySet()
			.forEach(entry -> stringBuilder.append(entry.getKey().getValue())
				.append(": ")
				.append(entry.getValue())
				.append("\r\n"));
	}

	private static void convertStatusLine(HttpResponse httpResponse, StringBuilder stringBuilder) {
		stringBuilder.append(httpResponse.getHttpStatusLine().getHttpVersion()).append(" ");
		stringBuilder.append(httpResponse.getHttpStatusLine().getStatusCode().getStatusCode()).append(" ");
		stringBuilder.append(httpResponse.getHttpStatusLine().getStatusCode().name()).append(" \r\n");
	}
}
