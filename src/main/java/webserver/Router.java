package webserver;

import utils.fp.Pair;
import utils.io.FileIoUtils;
import utils.parser.JsonObject;
import utils.parser.KeyValueParserFactory;
import webserver.http.startline.HttpMethod;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Router {
    private static final String ROUTER_CONFIG_PATH = "./router-config.json";

    private static final Router instance = new Router();

    private final Map<HttpMethod, Map<String, RoutedDestination>> mappingTable;

    public static Router getInstance() {
        return instance;
    }

    private Router() {
        this.mappingTable = FileIoUtils.loadFileFromClasspath(ROUTER_CONFIG_PATH).map(config ->
                KeyValueParserFactory.jsonParser().interpret(config)
        ).map(json ->
                json.entrySet().stream().map(perMethod ->
                        new Pair<>(
                                HttpMethod.of(perMethod.getKey()).get(),
                                ((JsonObject) perMethod.getValue()).entrySet().stream().map(perRoute ->
                                        new Pair<>(
                                                perRoute.getKey(),
                                                new RoutedDestination((JsonObject) perRoute.getValue())
                                        )
                                ).collect(Collectors.toMap(Pair::fst, Pair::snd))
                        )
                ).collect(Collectors.toMap(Pair::fst, Pair::snd))
        ).orElse(null);
    }

    public Optional<RoutedDestination> routeTo(HttpMethod method, String src) {
        return Optional.ofNullable(this.mappingTable.get(method).get(src));
    }
}