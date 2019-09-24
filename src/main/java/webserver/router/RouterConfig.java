package webserver.router;

import utils.fp.tuple.Pair;
import utils.io.FileIoUtils;
import utils.parser.simple.KeyValueParserFactory;
import utils.parser.json.JsonObject;
import webserver.http.HttpMethod;
import webserver.http.HttpPath;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RouterConfig {
    private static final String ROUTER_CONFIG_FILE_PATH = "./router-config.json";

    private static final RouterConfig instance = new RouterConfig();

    private final Map<HttpMethod, Pathfinder> config;

    public static RouterConfig getInstance() {
        return instance;
    }

    private RouterConfig() {
        this.config = FileIoUtils.loadFileFromClasspath(ROUTER_CONFIG_FILE_PATH).map(file ->
                KeyValueParserFactory.jsonParser().interpret(file)
        ).map(config ->
                config.attributeSet().stream().map(perMethodType ->
                        new Pair<>(
                                HttpMethod.of(perMethodType.getKey()).get(),
                                new Pathfinder((JsonObject) perMethodType.getValue())
                        )
                ).collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(Pair::fst, Pair::snd),
                                Collections::unmodifiableMap
                        )
                )
        ).orElse(Collections.emptyMap());
    }

    public Optional<MappedDestination> match(HttpMethod methodType, HttpPath queriedPath) {
        return Optional.ofNullable(this.config.get(methodType)).map(matcher -> matcher.get(queriedPath));
    }
}