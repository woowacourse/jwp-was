package utils.parser;

import utils.exception.NotFoundContentTypeSeparatorException;
import webserver.http.headerfields.Chemical;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContentTypeParser extends KeyValueParser {
    private static final String PAIR_DELIMITER = ";";
    private static final String KEY_VALUE_DELIMITER = "=";

    private static final String SLASH = "/";
    private static final String SEMICOLON = ";";

    private static final int START_INDEX = 0;
    private static final int TWO_SIZE = 2;

    public static Map<String, String> toMap(String input) {
        return toMap(PAIR_DELIMITER, KEY_VALUE_DELIMITER, input);
    }

    public static List<String> convertContentType(String input) {
        return Arrays.stream(input.split(SEMICOLON, TWO_SIZE))
                .map(data -> data.toLowerCase().trim())
                .collect(Collectors.toList());
    }

    public static Chemical chemicalParse(String line) {
        if (existSlash(line)) {
            final int slashIndex = line.indexOf(SLASH);
            return Chemical.valueOf(line.substring(START_INDEX, slashIndex).toUpperCase());
        }

        throw new NotFoundContentTypeSeparatorException();
    }

    public static String subtypeParse(String line) {
        if (existSlash(line)) {
            final int splitIndex = line.indexOf(SLASH) + 1;
            return line.substring(splitIndex);
        }

        throw new NotFoundContentTypeSeparatorException();
    }

    private static boolean existSlash(String line) {
        return line.contains(SLASH);
    }
}