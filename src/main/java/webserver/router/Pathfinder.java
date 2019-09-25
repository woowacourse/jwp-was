package webserver.router;

import utils.fp.tuple.Pair;
import utils.parser.json.JsonArray;
import utils.parser.json.JsonObject;
import webserver.http.HttpPath;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Pathfinder {
    //미구현
    private static final Pattern PATH_VAR = Pattern.compile("\\{[\\w\\W]*}");
    private final Map<String, MappedDestination> mappings;

    public static Optional<Pathfinder> of(JsonArray routes) {
        final Map<String, MappedDestination> mappings = routes.stream()
                                                                .map(x -> (JsonObject) x)
                                                                .map(x ->
                                                                    MappedDestination.of(
                                                                            (String) x.get("class").val(),
                                                                            (String) x.get("method").val()
                                                                    ).map(dest ->
                                                                            new Pair<>(
                                                                                    (String) x.get("path").val(),
                                                                                    dest
                                                                            )
                                                                    )
                                                                ).filter(Optional::isPresent)
                                                                .map(Optional::get)
                                                                .collect(
                                                                        Collectors.collectingAndThen(
                                                                                Collectors.toMap(Pair::fst, Pair::snd),
                                                                                Collections::unmodifiableMap
                                                                        )
                                                                );
        return (routes.size() == mappings.size()) ? Optional.of(new Pathfinder(mappings)) : Optional.empty();
    }

    private Pathfinder(Map<String, MappedDestination> mappings) {
        this.mappings = mappings;
    }

    public MappedDestination get(HttpPath path) {
        return this.mappings.get(path.toString());
    }
}