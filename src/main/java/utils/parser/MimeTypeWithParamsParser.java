package utils.parser;

import utils.fp.tuple.Pair;
import utils.parser.simple.KeyValueParserFactory;
import webserver.http.HttpMimeType;

import java.util.Map;
import java.util.Optional;

public class MimeTypeWithParamsParser {
    public static Optional<Pair<HttpMimeType, Map<String, String>>> interpret(String input) {
        return HttpMimeType.of(
                input.contains(";") ? input.substring(0, input.indexOf(";")) : input
        ).map(mimeType ->
                new Pair<>(
                        mimeType,
                        KeyValueParserFactory.contentTypeParser().interpret(input)
                )
        );
    }
}