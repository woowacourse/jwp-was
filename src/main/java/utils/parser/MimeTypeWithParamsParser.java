package utils.parser;

import utils.fp.tuple.Pair;
import webserver.httpelement.HttpMimeType;

import java.util.Map;
import java.util.Optional;

public final class MimeTypeWithParamsParser {
    public static Optional<Pair<HttpMimeType, Map<String, String>>> interpret(String input) {
        return HttpMimeType.of(
                input.contains(";") ? input.substring(0, input.indexOf(";")) : input
        ).map(mimeType ->
                new Pair<>(
                        mimeType,
                        KeyValueParserFactory.httpHeaderFieldAttrParser().interpret(input)
                )
        );
    }
}