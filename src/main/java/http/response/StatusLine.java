package http.response;

import utils.StringUtils;

public class StatusLine {
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
        return String.format("%s %s", version, responseStatus.serialize());
    }
}
