package webserver.http.response.utils;

import static utils.HeaderIOUtils.*;

import webserver.http.response.HttpResponse;

public class HttpResponseConverter {

	private static final String HEADER_LINE_REGEX = "\r\n";

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
				.append(HEADER_REGEX)
				.append(entry.getValue())
				.append(HEADER_LINE_REGEX));
	}

	private static void convertStatusLine(HttpResponse httpResponse, StringBuilder stringBuilder) {
		stringBuilder.append(httpResponse.getHttpStatusLine().getHttpVersion())
			.append(" ");
		stringBuilder.append(httpResponse.getStatusCodeNumber())
			.append(" ");
		stringBuilder.append(httpResponse.getStatusCodeName())
			.append(HEADER_LINE_REGEX);
	}
}
