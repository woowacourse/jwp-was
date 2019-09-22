package utils.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonParser implements KeyValueParser<JsonObject> {
    private static final Pattern STRING = Pattern.compile("\"([^\"]*)\"");
    private static final Pattern INTEGER = Pattern.compile("^-?[0-9]+$");
    private static final Pattern REAL = Pattern.compile("^-?(([0-9]+\\.[0-9]+)|([0-9].[0-9]+[eE][+\\-]?[0-9]+))$");
    private static final Predicate<String> BOOLEAN = token ->
            token.equalsIgnoreCase("true") || token.equalsIgnoreCase("false");
    private static final Predicate<String> MAYBE_OBJECT = token ->
            token.charAt(0) == '{' && token.charAt(token.length() - 1) == '}';
    private static final Predicate<String> MAYBE_ARRAY = token ->
            token.charAt(0) == '[' && token.charAt(token.length() - 1) == ']';
    private static final Predicate<String> NULL = token -> token.equalsIgnoreCase("null");
    private static final Pattern REPLACED_STRING = Pattern.compile("^%[0-9]+$");
    private static final SimpleStringParser attributesParser = new JsonAttributesParser();

    @Override
    public JsonObject interpret(String input) {
        if (!MAYBE_OBJECT.test(input.trim())) {
            return null;
        }
        final Matcher stringTokenMatcher = STRING.matcher(input);
        final List<String> stringTokens = new ArrayList<>();
        final StringBuffer tmp = new StringBuffer();
        int index = 0;
        while (stringTokenMatcher.find()) {
            stringTokens.add(unwrapEnclosure(stringTokenMatcher.group()));
            stringTokenMatcher.appendReplacement(tmp, "%" + index++);
        }
        stringTokenMatcher.appendTail(tmp);
        return parseObject(tmp.toString().replaceAll("\\s+", "").replaceAll(",{2,}", ","), stringTokens);
    }

    private String unwrapEnclosure(String input) {
        return input.substring(1, input.length() - 1).trim();
    }

    private JsonValue<?> parseValue(String token, List<String> stringTokens) {
        if (REPLACED_STRING.matcher(token).find()) {
            return parseString(token, stringTokens);
        }
        if (INTEGER.matcher(token).find()) {
            return new JsonInteger(Integer.parseInt(token));
        }
        if (REAL.matcher(token).find()) {
            return new JsonReal(Double.parseDouble(token));
        }
        if (BOOLEAN.test(token)) {
            return new JsonBoolean(Boolean.parseBoolean(token));
        }
        if (MAYBE_OBJECT.test(token)) {
            return parseObject(token, stringTokens);
        }
        if (MAYBE_ARRAY.test(token)) {
            return parseArray(token, stringTokens);
        }
        if (NULL.test(token)) {
            return JsonValue.NULL();
        }
        return null;
    }

    private JsonObject parseObject(String token, List<String> stringTokens) {
        final String innerContent = unwrapEnclosure(token);
        if (innerContent.isEmpty()) {
            return new JsonObject();
        }
        final Map<String, JsonValue<?>> val = parseAttributes(innerContent, stringTokens);
        return !val.isEmpty() ? new JsonObject(val) : null;
    }

    private Map<String, JsonValue<?>> parseAttributes(String input, List<String> stringTokens) {
        return attributesParser.interpret(input).entrySet()
                                                .stream()
                                                .map(x -> new JsonValue<?>[] {
                                                        parseString(x.getKey(), stringTokens),
                                                        parseValue(x.getValue(), stringTokens)
                                                }).collect(Collectors.toMap(attr ->
                                                    (String) attr[0].val(),
                                                    attr -> attr[1])
                                                );
    }

    private JsonArray parseArray(String token, List<String> stringTokens) {
        final String innerContent = unwrapEnclosure(token);
        if (innerContent.isEmpty()) {
            return new JsonArray();
        }
        final List<JsonValue<?>> elements = Stream.of(innerContent.split(","))
                                                .map(x -> parseValue(x, stringTokens))
                                                .collect(Collectors.toList());
        return !elements.contains(null) ? new JsonArray(elements) : null;
    }

    private JsonString parseString(String token, List<String> stringTokens) {
        return new JsonString(stringTokens.get(Integer.parseInt(token.substring(1))));
    }
}