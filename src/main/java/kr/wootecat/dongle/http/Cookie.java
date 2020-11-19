package kr.wootecat.dongle.http;

import static java.lang.String.*;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.wootecat.dongle.http.exception.IllegalCookieFormatException;

public class Cookie {

    public static final String PATH_ATTRIBUTE_NAME = "Path";

    private static final Pattern PATH_PATTERN = Pattern.compile("/\\w*");
    private static final String ILLEGAL_PATH_PATTERN_EXCEPTION_MESSAGE_FORMAT = "쿠키의 path 패턴이 올바른 형태가 아닙니다.: %s";

    private final String name;
    private final String value;
    private final String path;

    public Cookie(String name, String value) {
        this(name, value, null);
    }

    public Cookie(String name, boolean value) {
        this(name, String.valueOf(value), null);
    }

    public Cookie(String name, boolean value, String path) {
        this(name, String.valueOf(value), path);
    }

    public Cookie(String name, String value, String path) {
        validate(name, value, path);
        this.name = name;
        this.value = value;
        this.path = path;
    }

    private void validate(String name, String value, String path) {
        try {
            Objects.requireNonNull(name);
            Objects.requireNonNull(value);
            validatePath(path);
        } catch (RuntimeException e) {
            throw new IllegalCookieFormatException(e.getMessage());
        }
    }

    private void validatePath(String path) {
        if (path == null) {
            return;
        }

        Matcher pathPatternMatcher = PATH_PATTERN.matcher(path);
        if (!pathPatternMatcher.matches()) {
            throw new RuntimeException(format(ILLEGAL_PATH_PATTERN_EXCEPTION_MESSAGE_FORMAT, path));
        }
    }

    public boolean hasPath() {
        return path != null;
    }

    public boolean hasValue(String name, String value) {
        return Objects.equals(name, this.name) &&
                Objects.equals(value, this.value);
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }
}
