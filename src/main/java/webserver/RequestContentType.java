package webserver;

import utils.UrlEncodedParser;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public enum RequestContentType {
    X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded",
        bytes -> UrlEncodedParser.parse(new String(bytes)));

    private final String contentType;
    private final Function<byte[], Map<String, ?>> bodyParser;

    RequestContentType(String contentType, Function<byte[], Map<String, ?>> bodyParser) {
        this.contentType = contentType;
        this.bodyParser = bodyParser;
    }

    public String getContentType() {
        return contentType;
    }

    public Map<String, ?> parse(byte[] body) {
        return bodyParser.apply(body);
    }

    public static Optional<RequestContentType> from(String contentTypeHeader) {
        return Arrays.stream(values())
            .filter(contentType -> contentType.contentType.equals(contentTypeHeader))
            .findFirst();
    }
}
