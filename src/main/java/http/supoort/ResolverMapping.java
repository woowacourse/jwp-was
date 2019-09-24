package http.supoort;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResolverMapping {
    private final String regex;
    private final Pattern pattern;

    public ResolverMapping(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    public boolean match(String uri) {
        Matcher matcher = pattern.matcher(uri);
        return matcher.find();
    }
}
