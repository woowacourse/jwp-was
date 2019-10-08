package webserver.env;

import webserver.httpelement.HttpMethod;
import webserver.httpelement.HttpPath;

import java.util.Map;
import java.util.Optional;

public abstract class RouterConfig {
    protected final Map<HttpMethod, Pathfinder> matchers;

    protected RouterConfig(Map<HttpMethod, Pathfinder> matchers) {
        this.matchers = matchers;
    }

    public Optional<MappedDestination> match(HttpMethod methodType, HttpPath queriedPath) {
        return Optional.ofNullable(this.matchers.get(methodType)).map(matcher -> matcher.get(queriedPath));
    }
}