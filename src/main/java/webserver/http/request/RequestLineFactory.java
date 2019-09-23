package webserver.http.request;

public class RequestLineFactory {
    private static final String DELIMITER = " ";

    private RequestLineFactory() {
    }

    static RequestLine generate(final String requestLine) {
        final String[] split = requestLine.split(DELIMITER);
        final HttpMethod method = HttpMethod.getHttpMethod(split[0]);
        final String url = split[1];
        final     HttpVersion version = HttpVersion.of(split[2]);

        return RequestLine.of(method, url, version);
    }
}
