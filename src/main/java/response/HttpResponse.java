package response;

import java.util.Objects;
import resource.ContentType;

public class HttpResponse {

    private final static String httpVersion = "1.1";
    private final static String charset = "UTF-8";

    private StatusCode statusCode;
    private byte[] body;
    private ContentType contentType;
    private String location = "";

    public HttpResponse(StatusCode statusCode) {
        this.statusCode = statusCode;
        this.body = new byte[0];
    }

    public HttpResponse(StatusCode statusCode, byte[] body, ContentType contentType) {
        this.statusCode = statusCode;
        this.body = body;
        this.contentType = contentType;
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

        buildStatusLine(stringBuilder);
        stringBuilder.append("\r\n");

        if (hasLocation()) {
            buildLocationHeader(stringBuilder);
            stringBuilder.append("\r\n");
        }

        if (hasBody()) {
            buildContentTypeHeader(stringBuilder);
            stringBuilder.append("\r\n");

            buildContentLengthHeader(stringBuilder);
            stringBuilder.append("\r\n");

            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }

    private void buildStatusLine(StringBuilder stringBuilder) {
        validateStringBuilderIsNull(stringBuilder);

        stringBuilder.append("HTTP/" + httpVersion + " ")
            .append(statusCode.getStatusCode())
            .append(" ")
            .append(statusCode.getStatusMessage())
            .append(" ");
    }

    private void buildLocationHeader(StringBuilder stringBuilder) {
        validateStringBuilderIsNull(stringBuilder);
        if (!hasLocation()) {
            throw new UnsupportedOperationException(
                "This method cannot be used because there is no location value.");
        }
        stringBuilder.append("Location: ")
            .append(location);
    }

    private void buildContentTypeHeader(StringBuilder stringBuilder) {
        validateBodyIsExist();
        validateStringBuilderIsNull(stringBuilder);
        stringBuilder.append("Content-Type: ")
            .append(contentType.getContentType())
            .append(";charset=")
            .append(charset);
    }

    private void buildContentLengthHeader(StringBuilder stringBuilder) {
        validateBodyIsExist();
        validateStringBuilderIsNull(stringBuilder);
        stringBuilder.append("Content-Length: ")
            .append(body.length);
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

    public byte[] getBody() {
        return body;
    }
}
