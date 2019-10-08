package webserver.env;

import utils.fp.tuple.Pair;
import utils.io.FileIoUtils;
import utils.parser.KeyValueParserFactory;
import utils.parser.jsonelements.JsonArray;
import webserver.httpelement.HttpMethod;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class RouterConfigFile extends RouterConfig {
    private static final String ROUTER_CONFIG_FILE_PATH = "./router-config.json";

    private RouterConfigFile() {
        super(
                FileIoUtils.loadFileFromClasspath(ROUTER_CONFIG_FILE_PATH).map(file ->
                    KeyValueParserFactory.jsonParser().interpret(file)
                ).map(config -> {
                    final Map<HttpMethod, Pathfinder> matchers = config.stream().map(perMethod ->
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
                }).orElseThrow(WrongRouterConfigException::new)
        );
    }
}