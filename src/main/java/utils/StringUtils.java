package utils;

import utils.fp.Done;
import utils.fp.TailCall;
import utils.fp.TailRecursion;

import java.util.stream.Stream;

public class StringUtils {
    private static TailRecursion<String> pascalToKebobCase(String s, int i, StringBuilder acc) {
        if (i == s.length()) {
            return (Done<String>) acc::toString;
        }
        final char c = s.charAt(i);
        return (Character.isUpperCase(c))
                ? (TailCall<String>) () -> pascalToKebobCase(s, i + 1, acc.append('-').append(c))
                : (TailCall<String>) () -> pascalToKebobCase(s, i + 1, acc.append(c));
    }

    public static String pascalToKebobCase(String s) {
        return (s.length() <= 1)
                ? s.toUpperCase()
                : pascalToKebobCase(s, 1, new StringBuilder(s.substring(0, 1))).get();
    }

    public static String screamingSnakeCaseToStartCase(String s) {
        return Stream.of(s.split("_")).map(String::toLowerCase)
                                        .map(x -> x.substring(0, 1).toUpperCase() + x.substring(1))
                                        .reduce("", (a, b) -> a + " " + b);
    }
}