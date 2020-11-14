package http;

public class HttpRequestLine {
    private static final String DELIMITER = " ";
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    public static final int REQUEST_LINE_ELEMENTS_COUNT = 3;

    private final HttpMethod httpMethod;
    private final HttpUrl httpUrl;
    private final String httpVersion;

    private HttpRequestLine(HttpMethod httpMethod, HttpUrl httpUrl, String httpVersion) {
        this.httpMethod = httpMethod;
        this.httpUrl = httpUrl;
        this.httpVersion = httpVersion;
    }

    public static HttpRequestLine from(String requestLine) {
        String[] tokens = requestLine.split(DELIMITER);
        validate(tokens);

        HttpMethod httpMethod = HttpMethod.from(tokens[METHOD_INDEX]);
        HttpUrl httpUrl = HttpUrl.from(tokens[URL_INDEX]);
        String httpVersion = tokens[VERSION_INDEX];
        return new HttpRequestLine(httpMethod, httpUrl, httpVersion);
    }

    private static void validate(String[] tokens) {
        if(tokens.length != REQUEST_LINE_ELEMENTS_COUNT) {
         throw new IllegalArgumentException("request line 의 구성요소(method, url, version)이 모두 존재하지 않습니다.");
        }
    }

    public String getQueryParam(String param) {
        return httpUrl.getParam(param);
    }

    public String getVersion() {
        return httpVersion;
    }

    public boolean isMethod(HttpMethod httpMethod) {
        return httpMethod == this.httpMethod;
    }

    public String getPath() {
        return httpUrl.getPath(httpUrl.getMimeType());
    }

    public MimeType getMimeType() {
        return httpUrl.getMimeType();
    }
}
