package http;

import java.util.Objects;

public class QueryString {
    private String queryParameters;

    public QueryString(String queryParameters) {
        this.queryParameters = queryParameters;
    }

    public String getQueryString() {
        return queryParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return queryParameters.equals(that.queryParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryParameters);
    }

    @Override
    public String toString() {
        return "QueryString{" +
                "queryParameters='" + queryParameters + '\'' +
                '}';
    }
}
