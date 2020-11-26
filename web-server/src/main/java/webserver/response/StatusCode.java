package webserver.response;

import exception.NotFoundStatusCodeException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import utils.StringUtils;

public enum StatusCode {
    CONTINUE(100, "Continue"),
    OK(200, "Ok"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    private final int code;
    private final String reasonPhase;

    StatusCode(int code, String reasonPhase) {
        this.code = code;
        this.reasonPhase = reasonPhase;
    }

    public static StatusCode find(String code) {
        return Arrays.stream(StatusCode.values())
            .filter(statusCode -> statusCode.isSameStatusCode(code))
            .findFirst()
            .orElseThrow(
                () -> new NotFoundStatusCodeException(code + "에 해당하는 StatusCode를 찾지 못했습니다!"));
    }

    private boolean isSameStatusCode(String code) {
        return this.code == Integer.parseInt(code);
    }

    public void write(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(code + StringUtils.SPACE + reasonPhase + StringUtils.SPACE);
    }
}
