package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticResourceMatcher {
    private static final String STATIC_RESOURCE_REGEX = "[^\\?%*:|\"\\<\\>]+[.][a-zA-Z]+";
    private static final Pattern STATIC_RESOURCE_PATTERN = Pattern.compile(STATIC_RESOURCE_REGEX);

    public static boolean isStaticResourcePath(String path) {
        Matcher matcher = STATIC_RESOURCE_PATTERN.matcher(path);
        return matcher.matches();
    }
}
