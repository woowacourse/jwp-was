package webserver.domain;

import java.net.URI;
import java.net.URISyntaxException;

public class Url {
    private final URI uri;
    private final String path;
    private final QueryParameter queries;

    public Url(final String url) throws URISyntaxException {
        this.uri = new URI(url);
        this.path = uri.getPath();
        this.queries = new QueryParameter(uri.getQuery());
    }

    public String getPath() {
        return path;
    }

    public QueryParameter getQueries() {
        return queries;
    }

    public String getQueryValue(final String queryKey) {
        return this.queries.getValue(queryKey);
    }

    @Override
    public String toString() {
        return uri.toString();
    }
}
