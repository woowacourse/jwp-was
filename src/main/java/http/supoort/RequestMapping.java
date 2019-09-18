package http.supoort;

import http.model.HttpMethod;
import http.model.HttpRequest;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestMapping implements Comparable<RequestMapping> {
    private final HttpMethod method;
    private final String regex;
    private final Pattern pattern;
    private final int patternLength;

    public RequestMapping(HttpMethod method, String regex) {
        this.method = method;
        this.regex = regex;
        this.patternLength = regex.length();
        this.pattern = Pattern.compile(regex);
    }

    public boolean match(HttpRequest httpRequest) {
        Matcher matcher = pattern.matcher(httpRequest.getUri().getResourceLocation());
        return httpRequest.getHttpMethod() == method
                && matcher.find();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMapping that = (RequestMapping) o;
        return method == that.method &&
                Objects.equals(regex, that.regex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, regex);
    }

    @Override
    public int compareTo(RequestMapping requestMapping) {
        return requestMapping.patternLength - this.patternLength;
    }
}
