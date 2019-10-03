package http.parser;

import http.common.HttpVersion;
import http.request.HttpMethod;
import http.request.HttpUri;
import http.request.RequestLine;

public class RequestLineParser {

    private static final String BLANK = " ";

    public static RequestLine parse(String requestLine) {
        String[] requestLineTokens = requestLine.split(BLANK);

        HttpMethod method = HttpMethod.valueOf(requestLineTokens[0].trim());
        HttpUri uri = HttpUriParser.parse(requestLineTokens[1].trim());
        HttpVersion version = HttpVersion.of(requestLineTokens[2].trim());

        return new RequestLine(method, uri, version);
    }
}
