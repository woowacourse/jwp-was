package http.response;

import http.response.exception.InvalidStatusLineException;
import utils.StringUtils;

public class StatusLine {
    private static final String STATUS_LINE_FORMAT = "%s %s";
    private final String version;
    private final ResponseStatus responseStatus;

    public StatusLine(String version, ResponseStatus responseStatus) {
        checkValidateStatusLine(version, responseStatus);
        this.version = version;
        this.responseStatus = responseStatus;
    }

    private void checkValidateStatusLine(String version, ResponseStatus responseStatus) {
        if (StringUtils.isEmpty(version) || responseStatus == null) {
            throw new InvalidStatusLineException();
        }
    }

    public String serialize() {
        return String.format(STATUS_LINE_FORMAT, version, responseStatus.serialize());
    }
}
