package webserver.router;

import utils.fp.tuple.Pair;
import utils.io.FileIoUtils;
import utils.parser.json.JsonArray;
import utils.parser.simple.KeyValueParserFactory;
import webserver.httpelement.HttpMethod;
import webserver.httpelement.HttpPath;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RouterConfig {
    private static final String ROUTER_CONFIG_FILE_PATH = "./router-config.json";

    private final Map<HttpMethod, Pathfinder> matchers;

    public static Optional<RouterConfig> init() {
        return FileIoUtils.loadFileFromClasspath(ROUTER_CONFIG_FILE_PATH).map(file ->
            KeyValueParserFactory.jsonParser().interpret(file)
        ).map(config -> {
            final Map<HttpMethod, Pathfinder> matchers = config.attributeSet().stream().map(perMethod ->
                HttpMethod.of(perMethod.getKey()).flatMap(method ->
                Pathfinder.of((JsonArray) perMethod.getValue()).map(pathfinder ->
                    new Pair<>(method, pathfinder)
                ))
            ).filter(Optional::isPresent)
            .map(Optional::get)
            .collect(
                    Collectors.collectingAndThen(
                            Collectors.toMap(Pair::fst, Pair::snd),
                            Collections::unmodifiableMap
                    )
            );
            return (config.size() == matchers.size()) ? matchers : null;
        }).map(RouterConfig::new);
    }

    private RouterConfig(Map<HttpMethod, Pathfinder> matchers) {
        this.matchers = matchers;
    }

    public Optional<MappedDestination> match(HttpMethod methodType, HttpPath queriedPath) {
        return Optional.ofNullable(this.matchers.get(methodType)).map(matcher -> matcher.get(queriedPath));
    }
}