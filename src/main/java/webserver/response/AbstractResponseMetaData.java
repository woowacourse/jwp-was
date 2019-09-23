package webserver.response;

import webserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractResponseMetaData implements ResponseMetaData {

    private static final String VERSION = "HTTP/1.1";
    protected static final String HEADER_DELIMITER = ": ";
    protected static final String HEADER_NEW_LINE = "\r\n";
    protected final HttpRequest httpRequest;
    protected final Map<String, String> responseHeaderFields = new HashMap<>();

    protected AbstractResponseMetaData(final HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public boolean hasBody() {
        return false;
    }

    @Override
    public byte[] getBody() {
        return new byte[0];
    }

    @Override
    public String getHttpResponseHeaderFields() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : responseHeaderFields.entrySet()) {
            builder.append(entry.getKey());
            builder.append(HEADER_DELIMITER);
            builder.append(entry.getValue());
            builder.append(HEADER_NEW_LINE);
        }
        builder.append(HEADER_NEW_LINE);

        return builder.toString();
    }

    @Override
    public String getResponseLine() {
        HttpStatus httpStatus = getHttpStatus();
        return String.join(" ", getVersion(), String.valueOf(httpStatus.getNumber()), httpStatus.name());
    }
}
