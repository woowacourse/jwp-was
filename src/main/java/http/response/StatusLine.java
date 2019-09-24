package http.response;

import http.common.HttpVersion;
import http.response.exception.InvalidStatusLineException;

public class StatusLine {
    private static final String STATUS_LINE_FORMAT = "%s %s";
    private final HttpVersion version;
    private final ResponseStatus responseStatus;

    public StatusLine(HttpVersion version, ResponseStatus responseStatus) {
        checkValidateStatusLine(version, responseStatus);
        this.version = version;
        this.responseStatus = responseStatus;
    }

    private void checkValidateStatusLine(HttpVersion version, ResponseStatus responseStatus) {
        if (version == null || responseStatus == null) {
            throw new InvalidStatusLineException();
        }
    }

    public String serialize() {
        return String.format(STATUS_LINE_FORMAT, version, responseStatus.serialize());
    }
}
