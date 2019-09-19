package webserver.http;

import utils.parser.KeyValueParserFactory;

import java.util.Collections;
import java.util.Map;

public class HttpContentType {
    enum Chemical {
        APPLICATION,
        AUDIO,
        EXAMPLE,
        FONT,
        IMAGE,
        MESSAGE,
        MODEL,
        MULTIPART,
        TEXT,
        VIDEO
    }

    private final Chemical chemical;
    private final String subtype;
    private final Map<String, String> params;

    public HttpContentType(String input, KeyValueParserFactory keyValueParserFactory) {
        final int slashIndex = input.indexOf("/");
        this.chemical = Chemical.valueOf(input.substring(0, slashIndex).toUpperCase());
        this.subtype = input.contains(";")
                ? input.substring(slashIndex + 1, input.indexOf(";"))
                : input.substring(slashIndex + 1);
        this.params = Collections.unmodifiableMap(keyValueParserFactory.contentTypeParser().toMap(input));
    }
}