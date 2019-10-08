package utils.parser;

import utils.fp.tailrecursion.Done;
import utils.fp.tailrecursion.TailCall;
import utils.fp.tailrecursion.TailRecursion;
import utils.fp.tuple.Pair;
import utils.fp.tuple.Triplet;
import utils.parser.jsonelements.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JsonParser implements KeyValueParser<JsonObject> {
    private static final Pattern REAL_EXP_NOTATION = Pattern.compile("-?[0-9]\\.[0-9]+[eE][+\\-][0-9]+");
    private static final Pattern REAL = Pattern.compile("-?[0-9]+\\.[0-9]+");

    protected JsonParser() {}

    @Override
    public JsonObject interpret(String input) {
        final String trimmed = input.trim();
        if (trimmed.charAt(0) == '{' && trimmed.charAt(trimmed.length() - 1) == '}') {
            return parseObject(trimmed, 0, trimmed.length() - 1);
        }
        return null;
    }

    public JsonObject interpret(byte[] input) {
        return interpret(new String(input));
    }

    private TailRecursion<Integer> jumpBlank(String input, int i) {
        return (input.substring(i, i + 1).matches("\\s"))
                ? (TailCall<Integer>) () -> jumpBlank(input, i + 1)
                : (Done<Integer>) () -> i;
    }

    private JsonObject parseObject(String input, int begin, int end) {
        if (input.substring(begin + 1, end).trim().isEmpty()) {
            return new JsonObject();
        }
        return Optional.ofNullable(parseAttributes(input, begin + 1, new HashMap<>()).get())
                        .map(JsonObject::new)
                        .orElseGet(JsonObject::new);
    }

    private TailRecursion<Map<String, JsonValue<?>>> parseAttributes(
            String input,
            int begin,
            Map<String, JsonValue<?>> acc
    ) {
        return Optional.ofNullable(parseAttribute(input, begin)).map(attr -> {
            acc.put(attr.fst(), attr.snd());
            final int nextLetterIndex = jumpBlank(input, attr.trd() + 1).get();
            final char nextLetter = input.charAt(nextLetterIndex);
            if (nextLetter == '}' || nextLetter == ']') {
                return (Done<Map<String, JsonValue<?>>>) () -> acc;
            }
            if (input.charAt(nextLetterIndex) == ',') {
                final int nextNextLetterIndex = jumpBlank(input, nextLetterIndex + 1).get();
                final char nextNextLetter = input.charAt(nextNextLetterIndex);
                return (nextNextLetter != '}' && nextNextLetter != ']')
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
        if (input.charAt(begin) != '"' ) {
           return null;
        }
        final int end = tokenizeString(input, begin + 1);
        return (begin < end) ? new Pair<>(input.substring(begin + 1, end), end) : null;
    }

    private Pair<? extends JsonValue<?>, Integer> lexValue(String input, int begin) {
        final char c = input.charAt(begin);
        switch (c) {
            case '{':
                return lexObject(input, begin);
            case '[':
                return lexArray(input, begin);
            case '"':
                return lexString(input, begin);
            case '-':
                return lexNumber(input, begin);
            case 't':
            case 'T':
            case 'f':
            case 'F':
                return lexBoolean(input, begin);
            case 'n':
                return lexNull(input, begin);
            default:
                return ('0' <= c && c <= '9') ? lexNumber(input, begin) : null;
        }
    }

    private TailRecursion<Integer> tokenizePossiblyNestedValue(
            String input,
            char openingToken,
            char closingToken,
            int i,
            int depth,
            boolean isInsideString
    ) {
        if (i == input.length()) {
            return (Done<Integer>) () -> -1;
        }
        final char c = input.charAt(i);
        if (c == '"' && input.charAt(i - 1) != '\\') {
            return (TailCall<Integer>) () ->
                    tokenizePossiblyNestedValue(input, openingToken, closingToken, i + 1, depth, !isInsideString);
        }
        if (c == closingToken && depth == 0 && !isInsideString) {
            return (Done<Integer>) () -> i;
        }
        if (c == openingToken && !isInsideString) {
            return (TailCall<Integer>) () ->
                    tokenizePossiblyNestedValue(input, openingToken, closingToken, i + 1, depth + 1, isInsideString);
        }
        if (c == closingToken && !isInsideString) {
            return (TailCall<Integer>) () ->
                    tokenizePossiblyNestedValue(input, openingToken, closingToken, i + 1, depth - 1, isInsideString);
        }
        return (TailCall<Integer>) () ->
                tokenizePossiblyNestedValue(input, openingToken, closingToken, i + 1, depth, isInsideString);
    }

    private Pair<? extends JsonValue<?>, Integer> lexObject(String input, int begin) {
        final int end = tokenizeObject(input, begin + 1);
        return (begin < end) ? new Pair<>(parseObject(input, begin, end), end) : null;
    }

    private int tokenizeObject(String input, int i) {
        return tokenizePossiblyNestedValue(input, '{', '}', i, 0, false).get();
    }

    private JsonArray parseArray(String input, int begin, int end) {
        if (input.substring(begin + 1, end).trim().isEmpty()) {
            return new JsonArray();
        }
        return Optional.ofNullable(parseArrayElements(input, begin + 1, new ArrayList<>()).get())
                        .map(JsonArray::new)
                        .orElseGet(JsonArray::new);
    }

    private TailRecursion<List<JsonValue<?>>> parseArrayElements(
            String input,
            int begin,
            List<JsonValue<?>> acc
    ) {
        return Optional.ofNullable(parseArrayElement(input, begin)).map(el -> {
            acc.add(el.fst());
            final int nextLetterIndex = jumpBlank(input, el.snd() + 1).get();
            if (input.charAt(nextLetterIndex) == ']') {
                return (Done<List<JsonValue<?>>>) () -> acc;
            }
            if (input.charAt(nextLetterIndex) == ',') {
                final int nextNextLetterIndex = jumpBlank(input, nextLetterIndex + 1).get();
                return (input.charAt(nextNextLetterIndex) != ']')
                        ? (TailCall<List<JsonValue<?>>>) () -> parseArrayElements(input, nextNextLetterIndex, acc)
                        : (Done<List<JsonValue<?>>>) () -> acc;
            }
            return null;
        }).orElse(null);
    }

    private Pair<? extends JsonValue<?>, Integer> parseArrayElement(String input, int begin) {
        return Optional.ofNullable(lexValue(input, jumpBlank(input, begin).get())).map(value ->
            new Pair<>(value.fst(), value.snd())
        ).orElse(null);
    }

    private Pair<? extends JsonValue<?>, Integer> lexArray(String input, int begin) {
        final int end = tokenizeArray(input, begin + 1);
        return (begin < end) ? new Pair<>(parseArray(input, begin, end), end) : null;
    }

    private int tokenizeArray(String input, int i) {
        return tokenizePossiblyNestedValue(input, '[', ']', i, 0, false).get();
    }

    private Pair<? extends JsonValue<?>, Integer> lexString(String input, int begin) {
        final int end = tokenizeString(input, begin + 1);
        return (begin < end) ? new Pair<>(new JsonString(input.substring(begin + 1, end)), end) : null;
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

    private int tokenizeString(String input, int i) {
        return tokenizeString(input, i, '"').get();
    }

    private Pair<? extends JsonValue<?>, Integer> lexNumber(String input, int begin) {
        final Matcher realExpNotationMatcher = REAL_EXP_NOTATION.matcher(input.substring(begin));
        if (realExpNotationMatcher.find() && realExpNotationMatcher.start() == 0) {
            return new Pair<>(
                    new JsonReal(Double.parseDouble(realExpNotationMatcher.group())),
                    realExpNotationMatcher.end() - 1 + begin
            );
        }
        final Matcher realMatcher = REAL.matcher(input.substring(begin));
        if (realMatcher.find() && realMatcher.start() == 0) {
            return new Pair<>(new JsonReal(Double.parseDouble(realMatcher.group())), realMatcher.end() - 1 + begin);
        }
        final int end = tokenizeInteger(input, begin + 1).get();
        if (end < 0) {
            return null;
        }
        try {
            return new Pair<>(new JsonInteger(Integer.parseInt(input.substring(begin, end))), end);
        } catch (NumberFormatException e) {
            return new Pair<>(new JsonLong(Long.parseLong(input.substring(begin, end))), end);
        }
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

    private Pair<? extends JsonValue<?>, Integer> lexBoolean(String input, int begin) {
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

    private int tokenizeBoolean(String input, int i) {
        if ((i + 3 < input.length()) && input.substring(i, i + 4).equalsIgnoreCase("true")) {
            return i + 3;
        }
        if ((i + 4 < input.length()) && input.substring(i, i + 5).equalsIgnoreCase("false")) {
            return i + 4;
        }
        return -1;
    }

    private Pair<? extends JsonValue<?>, Integer> lexNull(String input, int begin) {
        final int end = tokenizeNull(input, begin);
        return (begin < end) ? new Pair<>(JsonNull.get(), end) : null;
    }

    private int tokenizeNull(String input, int i) {
        return ((i + 3 < input.length()) && input.substring(i, i + 4).equalsIgnoreCase("null")) ? (i + 3) : -1;
    }
}