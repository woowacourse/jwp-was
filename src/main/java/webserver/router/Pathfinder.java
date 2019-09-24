package webserver.router;

import utils.fp.tuple.Pair;
import utils.parser.json.JsonObject;
import webserver.http.HttpPath;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Pathfinder {
    //미구현
    private static final Pattern PATH_VAR = Pattern.compile("\\{[\\w\\W]*}");
    private final Map<String, MappedDestination> config;

    public Pathfinder(JsonObject routes) {
        this.config = routes.attributeSet().stream().map(perRoute ->
                new Pair<>(
                        perRoute.getKey(),
                        new MappedDestination((JsonObject) perRoute.getValue())
                )
        ).collect(
                Collectors.collectingAndThen(
                        Collectors.toMap(Pair::fst, Pair::snd),
                        Collections::unmodifiableMap
                )
        );
    }

    public MappedDestination get(HttpPath path) {
        return this.config.get(path.toString());
    }
}