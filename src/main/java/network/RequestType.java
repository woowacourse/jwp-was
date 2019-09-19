package network;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public enum RequestType {
    STATIC("./static", url -> Pattern.compile("(/[0-9a-zA-Z\\-_]*)*(.*\\.((?!html).)*)$").matcher(url).matches()),
    TEMPLATES("./templates", url -> Pattern.compile("(/[0-9a-zA-Z\\-_]*)*(.*\\.html)$").matcher(url).matches()),
    LOGIC("", url -> Pattern.compile("(/[0-9a-zA-Z\\-_]*)*").matcher(url).matches());

    private String prefix;
    private Predicate<String> requestTypeChecker;

    RequestType(final String prefix, final Predicate<String> requestTypeChecker) {
        this.prefix = prefix;
        this.requestTypeChecker = requestTypeChecker;
    }

    public static RequestType value(String url) {
        return Arrays.stream(RequestType.values())
                .filter(requestType -> requestType.requestTypeChecker.test(url))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getPrefix() {
        return prefix;
    }
}
