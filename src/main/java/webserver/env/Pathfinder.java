package webserver.env;

import utils.fp.tuple.Pair;
import utils.fp.tuple.Triplet;
import utils.parser.jsonelements.JsonArray;
import utils.parser.jsonelements.JsonObject;
import webserver.httpelement.HttpPath;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class Pathfinder {
    private final Map<String, MappedDestination> mappings;

    protected static Optional<Pathfinder> of(JsonArray routes) {
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



    protected Pathfinder(Map<String, MappedDestination> mappings) {
        this.mappings = mappings;
    }

    public MappedDestination get(HttpPath path) {
        return this.mappings.get(path.toString());
    }

    public Set<Triplet<String, String, String>> summary() {
        return this.mappings.entrySet().stream().map(x ->
            new Triplet<>(x.getKey(), x.getValue().className(), x.getValue().methodName())
        ).collect(Collectors.toSet());
    }
}