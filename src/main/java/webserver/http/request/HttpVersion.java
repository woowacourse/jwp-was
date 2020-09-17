package webserver.http.request;

import exception.InvalidHttpMessageException;
import exception.InvalidHttpVersionException;
import utils.StringUtils;

public class HttpVersion {
    private static final String HTTP_VERSION_PREFIX = "HTTP/";

    private final String version;

    public HttpVersion(String version) {
        StringUtils.validateNonNullAndNotEmpty(() -> new InvalidHttpMessageException(version), version);
        validateIsStartsWithVersionPrefix(version);

        this.version = version;
    }

    public static HttpVersion from(double version) {
        return new HttpVersion(HTTP_VERSION_PREFIX + version);
    }

    private void validateIsStartsWithVersionPrefix(String version) {
        if (!version.startsWith(HTTP_VERSION_PREFIX)) {
            throw new InvalidHttpVersionException(version);
        }
    }

    public String toHttpMessage() {
        return version;
    }
}
