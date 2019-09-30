package http.request;

import http.common.UriExtension;
import http.parser.QueryParamsParser;

import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUri {

    private URI uri;

    public HttpUri(final URI uri) {
        this.uri = uri;
    }

    public String getPath() {
        return this.uri.getPath();
    }

    public String getQuery() {
        return this.uri.getQuery();
    }

    public String findPathPrefix() {
        return UriExtension.of(uri.getPath()).getPathPrefix();
    }

    public boolean isMatches(final String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(uri.getPath());
        return m.find();
    }

    public Map<String, String> getQueryParam() {
        return QueryParamsParser.parse(uri.getQuery());
    }

    public boolean isEqualsPath(final HttpUri aUri) {
        return uri.getPath().equals(aUri.getPath());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HttpUri uri1 = (HttpUri) o;
        return uri.equals(uri1.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }
}
