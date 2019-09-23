package webserver;

import utils.fp.Pair;
import utils.parser.json.JsonObject;
import webserver.http.HttpPath;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RouterPathMatcher {
    private static final Pattern PATH_VAR = Pattern.compile("\\{[\\w\\W]*}");
    private final Map<String, RouterMappedDestination> config;

    public RouterPathMatcher(JsonObject routes) {
        this.config = routes.attributeSet().stream().map(perRoute ->
                new Pair<>(
                        perRoute.getKey(),
                        new RouterMappedDestination((JsonObject) perRoute.getValue())
                )
        ).collect(
                Collectors.collectingAndThen(
                        Collectors.toMap(Pair::fst, Pair::snd),
                        Collections::unmodifiableMap
                )
        );
    }

    public RouterMappedDestination get(HttpPath path) {
        return this.config.get(path.toString());
    }
}