package http;

public class RequestLineParser {

    public static RequestLine parse(String requestLine) {
        String[] requestLineTokens = requestLine.split(" ");

        HttpMethod method = HttpMethod.valueOf(requestLineTokens[0].trim());
        HttpUri uri = new HttpUri(requestLineTokens[1].trim());
        HttpVersion version = HttpVersion.of(requestLineTokens[2].trim());

        return new RequestLine(method, uri, version);
    }
}
