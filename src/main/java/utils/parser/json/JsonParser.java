package utils.parser.json;

import utils.fp.*;
import utils.parser.KeyValueParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser implements KeyValueParser<JsonObject> {
    private static final Pattern REAL_EXP_NOTATION = Pattern.compile("-?[0-9]\\.[0-9]+[eE][+\\-][0-9]+");
    private static final Pattern REAL = Pattern.compile("-?[0-9]+\\.[0-9]+");

    @Override
    public JsonObject interpret(String input) {
        final String trimmed = input.trim();
        if (trimmed.charAt(0) == '{' && trimmed.charAt(trimmed.length() - 1) == '}') {
            return parseObject(trimmed, 0, trimmed.length() - 1);
        }
        return null;
    }

    private String unwrapEnclosure(String input) {
        return input.substring(1, input.length() - 1).trim();
    }

    private TailRecursion<Integer> jumpBlank(String input, int i) {
        return (input.substring(i, i + 1).matches("\\s"))
                ? (TailCall<Integer>) () -> jumpBlank(input, i + 1)
                : (Done<Integer>) () -> i;
    }

    private JsonObject parseObject(String input, int begin, int end) {
        if (unwrapEnclosure(input.substring(begin, end + 1)).trim().isEmpty()) {
            return new JsonObject();
        }
        return new JsonObject(parseAttributes(input, begin + 1, new HashMap<>()).get());
    }

    private TailRecursion<Map<String, JsonValue<?>>> parseAttributes(
            String input,
            int begin,
            Map<String, JsonValue<?>> acc
    ) {
        return Optional.ofNullable(parseAttribute(input, begin)).map(attr -> {
            acc.put(attr.fst(), attr.snd());
            final int nextLetterIndex = jumpBlank(input, attr.trd() + 1).get();
            if (input.charAt(nextLetterIndex) == '}') {
                return (Done<Map<String, JsonValue<?>>>) () -> acc;
            }
            if (input.charAt(nextLetterIndex) == ',') {
                final int nextNextLetterIndex = jumpBlank(input, nextLetterIndex + 1).get();
                return (input.charAt(nextNextLetterIndex) != '}')
                        ? (TailCall<Map<String, JsonValue<?>>>) () -> parseAttributes(input, nextNextLetterIndex, acc)
                        : (Done<Map<String, JsonValue<?>>>) () -> acc;
            }
            return null;
        }).orElse(null);
    }

    private Triplet<String, ? extends JsonValue<?>, Integer> parseAttribute(String input, int begin) {
        return Optional.ofNullable(lexKey(input, jumpBlank(input, begin).get())).flatMap(key -> {
            final int colonIndex = jumpBlank(input, key.snd() + 1).get();
            if (input.charAt(colonIndex) != ':') {
                return Optional.empty();
            }
            return Optional.ofNullable(lexValue(input, jumpBlank(input, colonIndex + 1).get())).map(value ->
                new Triplet<>(key.fst(), value.fst(), value.snd())
            );
        }).orElse(null);
    }

    private Pair<String, Integer> lexKey(String input, int begin) {
        if (input.charAt(begin) != '"') {
           return null;
        }
        final int end = tokenizeString(input, begin + 1);
        return (begin < end) ? new Pair<>(input.substring(begin + 1, end), end) : null;
    }

    private Pair<? extends JsonValue<?>, Integer> lexValue(String input, int begin) {
        final char c = input.charAt(begin);
        if (c == '"') {
            final int end = tokenizeString(input, begin + 1);
            return (begin < end) ? new Pair<>(new JsonString(input.substring(begin + 1, end)), end) : null;
        }
        if (c == '-' || ('0' <= c && c <= '9')) {
            final Matcher realExpNotationMatcher = REAL_EXP_NOTATION.matcher(input.substring(begin));
            if (realExpNotationMatcher.find() && realExpNotationMatcher.start() == 0) {
                return new Pair<>(
                        new JsonReal(Double.parseDouble(realExpNotationMatcher.group())),
                        realExpNotationMatcher.end() + begin
                );
            }
            final Matcher realMatcher = REAL.matcher(input.substring(begin));
            if (realMatcher.find() && realMatcher.start() == 0) {
                return new Pair<>(new JsonReal(Double.parseDouble(realMatcher.group())), realMatcher.end() + begin);
            }
            final int end = tokenizeInteger(input, begin + 1).get();
            return (begin < end)
                    ? new Pair<>(new JsonInteger(Integer.parseInt(input.substring(begin, end))), end)
                    : null;
        }
        if (c == 't' || c == 'T' || c == 'f' || c == 'F') {
            final int end = tokenizeBoolean(input, begin);
            switch (end - begin) {
                case 3:
                    return new Pair<>(new JsonBoolean(true), end);
                case 4:
                    return new Pair<>(new JsonBoolean(false), end);
                default:
                    return null;
            }
        }
        if (c == '{') {
            final int end = tokenizeObject(input, begin + 1);
            return (begin < end) ? new Pair<>(parseObject(input, begin, end), end) : null;
        }
        //미구현
        if (c == '[') {
            final int end = tokenizeArray(input, begin + 1);
            return (begin < end) ? new Pair<>(JsonValue.NULL(), end) : null;
        }
        if (c == 'n') {
            final int end = tokenizeNull(input, begin);
            return (begin < end) ? new Pair<>(JsonValue.NULL(), end) : null;
        }
        return null;
    }

    private int tokenizeString(String input, int i) {
        return tokenizeString(input, i, '"').get();
    }

    private TailRecursion<Integer> tokenizeString(String input, int i, char lookbehind) {
        if (i == input.length()) {
            return (Done<Integer>) () -> -1;
        }
        final char c = input.charAt(i);
        return (c == '"' && lookbehind != '\\')
                ? (Done<Integer>) () -> i
                : (TailCall<Integer>) () -> tokenizeString(input, i + 1, c);
    }

    private TailRecursion<Integer> tokenizeInteger(String input, int i) {
        if (i == input.length()) {
            return (Done<Integer>) () -> -1;
        }
        final char c = input.charAt(i);
        return ('0' <= c && c <= '9')
                ? (TailCall<Integer>) () -> tokenizeInteger(input, i + 1)
                : (Done<Integer>) () -> i;
    }

    private int tokenizeBoolean(String input, int i) {
        if ((i + 3 < input.length()) && input.substring(i, i + 4).equalsIgnoreCase("true")) {
            return i + 3;
        }
        if ((i + 4 < input.length()) && input.substring(i, i + 5).equalsIgnoreCase("false")) {
            return i + 4;
        }
        return -1;
    }

    private int tokenizeNull(String input, int i) {
        return ((i + 3 < input.length()) && input.substring(i, i + 4).equalsIgnoreCase("null")) ? (i + 3) : -1;
    }

    private TailRecursion<Integer> tokenizePossiblyNestedValue(String input, char closingToken, int i, int depth) {
        if (i == input.length()) {
            return (Done<Integer>) () -> -1;
        }
        final char c = input.charAt(i);
        if (c == closingToken && depth == 0) {
            return (Done<Integer>) () -> i;
        }
        if (c == '{' || c == '[') {
            return (TailCall<Integer>) () -> tokenizePossiblyNestedValue(input, closingToken, i + 1, depth + 1);
        }
        if (c == '}' || c == ']') {
            return (TailCall<Integer>) () -> tokenizePossiblyNestedValue(input, closingToken, i + 1, depth - 1);
        }
        return (TailCall<Integer>) () -> tokenizePossiblyNestedValue(input, closingToken, i + 1, depth);
    }

    private int tokenizeObject(String input, int i) {
        return tokenizePossiblyNestedValue(input, '}', i, 0).get();
    }

    private int tokenizeArray(String input, int i) {
        return tokenizePossiblyNestedValue(input, ']', i, 0).get();
    }
}