package response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import resource.ContentType;

public class HttpResponse {

    private final static String httpVersion = "1.1";
    private final static String charset = "UTF-8";

    private StatusCode statusCode;
    private String location = "";
    private byte[] body;
    private ContentType contentType;
    private ResponseCookies cookies = ResponseCookies.EMPTY_COOKIES;

    public HttpResponse(StatusCode statusCode) {
        this.statusCode = statusCode;
        this.body = new byte[0];
    }

    public HttpResponse(StatusCode statusCode, byte[] body, ContentType contentType) {
        this.statusCode = statusCode;
        this.body = body;
        this.contentType = contentType;
    }

    public HttpResponse(StatusCode statusCode, String body, ContentType contentType) {
        this(statusCode, body.getBytes(), contentType);
    }

    public HttpResponse(StatusCode statusCode, String location) {
        this(statusCode);
        this.location = location;
    }

    private boolean hasBody() {
        return body.length > 0;
    }

    private boolean hasLocation() {
        return !Objects.isNull(location) && !location.isEmpty();
    }

    public String buildHeader() {
        StringBuilder stringBuilder = new StringBuilder();

        buildStatusLine(stringBuilder).append("\r\n");

        if (hasLocation()) {
            buildLocationHeader(stringBuilder).append("\r\n");
        }
        if (cookies.isNotEmpty()) {
            buildCookieHeader(stringBuilder).append("\r\n");
        }
        if (hasBody()) {
            buildContentTypeHeader(stringBuilder).append("\r\n");
            buildContentLengthHeader(stringBuilder).append("\r\n");

            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }

    private StringBuilder buildStatusLine(StringBuilder stringBuilder) {
        validateStringBuilderIsNull(stringBuilder);

        stringBuilder.append("HTTP/" + httpVersion + " ")
            .append(statusCode.getStatusCode())
            .append(" ")
            .append(statusCode.getStatusMessage())
            .append(" ");

        return stringBuilder;
    }

    private StringBuilder buildLocationHeader(StringBuilder stringBuilder) {
        validateStringBuilderIsNull(stringBuilder);
        if (!hasLocation()) {
            throw new UnsupportedOperationException(
                "This method cannot be used because there is no location value.");
        }
        stringBuilder.append("Location: ")
            .append(location);

        return stringBuilder;
    }

    private StringBuilder buildCookieHeader(StringBuilder stringBuilder) {
        List<String> cookieHeaderValues = cookies.toCookieHeaderValueFormats();

        String cookieHeader = cookieHeaderValues.stream()
            .map(cookieHeaderValue -> "Set-Cookie: " + cookieHeaderValue)
            .collect(Collectors.joining("\r\n"));
        stringBuilder.append(cookieHeader);

        return stringBuilder;
    }

    private StringBuilder buildContentTypeHeader(StringBuilder stringBuilder) {
        validateBodyIsExist();
        validateStringBuilderIsNull(stringBuilder);
        stringBuilder.append("Content-Type: ")
            .append(contentType.getContentType())
            .append(";charset=")
            .append(charset);

        return stringBuilder;
    }

    private StringBuilder buildContentLengthHeader(StringBuilder stringBuilder) {
        validateBodyIsExist();
        validateStringBuilderIsNull(stringBuilder);
        stringBuilder.append("Content-Length: ")
            .append(body.length);

        return stringBuilder;
    }

    private void validateStringBuilderIsNull(StringBuilder stringBuilder) {
        if (Objects.isNull(stringBuilder)) {
            throw new IllegalArgumentException("The parameter string builder must be passed.");
        }
    }

    private void validateBodyIsExist() {
        if (!hasBody()) {
            throw new UnsupportedOperationException(
                "cannot use this method because there is no body.");
        }
    }

    public HttpResponse setCookies(ResponseCookies cookies) {
        if (Objects.isNull(cookies)) {
            throw new IllegalArgumentException("cannot use null parameter.");
        }
        this.cookies = cookies;

        return this;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "** HttpResponse **\n"
            + buildHeader()
            + "\n";
    }
}
