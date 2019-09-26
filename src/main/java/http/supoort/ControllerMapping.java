package http.supoort;

import http.model.HttpMethod;
import http.model.HttpRequest;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerMapping implements Comparable<ControllerMapping> {
    private final HttpMethod method;
    private final String regex;
    private final Pattern pattern;

    public ControllerMapping(HttpMethod method, String regex) {
        this.method = method;
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    public boolean match(HttpRequest httpRequest) {
        Matcher matcher = pattern.matcher(httpRequest.getRequestLine().getHttpUri().getResourceLocation());
        return httpRequest.getRequestLine().getHttpMethod() == method
                && matcher.find();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControllerMapping that = (ControllerMapping) o;
        return method == that.method &&
                Objects.equals(regex, that.regex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, regex);
    }

    @Override
    public int compareTo(ControllerMapping controllerMapping) {
        return controllerMapping.regex.length() - this.regex.length();
    }
}
