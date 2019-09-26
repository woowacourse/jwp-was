package http.request;

public class InvalidHttpRequestStartLine extends RuntimeException {
    private InvalidHttpRequestStartLine(String startLine) {
        super(String.format("올바르지 않은 요청입니다. [%s]", startLine));
    }

    public static InvalidHttpRequestStartLine of(String startLine) {
        return new InvalidHttpRequestStartLine(startLine);
    }
}
