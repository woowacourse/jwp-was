package webserver.env;

import utils.fp.tuple.Pair;
import utils.fp.tuple.Triplet;
import utils.io.FileIoUtils;
import utils.parser.jsonelements.JsonArray;
import utils.parser.jsonelements.JsonObject;
import utils.parser.jsonelements.JsonString;
import utils.parser.jsonelements.JsonValue;
import webserver.httpelement.HttpMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class RouterConfigAnnotation extends RouterConfig {
    private static Map<HttpMethod, Pathfinder> init() {
        final Map<HttpMethod, Pathfinder> matchers = Env.reflections().getAllMethodsAnnotatedX(RequestMapping.class)
                                                                        .map(dest -> {
            final RequestMapping annotation = dest.getAnnotation(RequestMapping.class);
            return new Triplet<>(annotation.method(), annotation.path(), dest);
        }).collect(
                Collectors.groupingBy(
                        Triplet::fst,
                        Collectors.mapping(x -> new Pair<>(x.snd(), x.trd()), Collectors.toList())
                )
        ).entrySet().stream().map(x ->
                new Pair<>(
                        x.getKey(),
                        x.getValue().stream().collect(
                                Collectors.toMap(
                                        Pair::fst,
                                        y -> new MappedDestination(y.snd())
                                )
                        )
                )
        ).collect(
                Collectors.toMap(Pair::fst, x -> new Pathfinder(x.snd()))
        );
        saveConfigToFile(matchers);
        return matchers;
    }

    private static void saveConfigToFile(Map<HttpMethod, Pathfinder> matchers) {
        FileIoUtils.saveFileAtClasspath(
                "router-config.json",
                new JsonObject(
                        matchers.entrySet().stream().map(x ->
                            x.getValue().summary().stream().map(y ->
                                new Pair<>(
                                        x.getKey().toString(),
                                        new JsonObject(
                                            new HashMap<String, JsonValue<?>>() {{
                                                this.put("path", new JsonString(y.fst()));
                                                this.put("class", new JsonString(y.snd()));
                                                this.put("method", new JsonString(y.trd()));
                                            }}
                                        )
                                )
                            ).collect(Collectors.groupingBy(Pair::fst)).entrySet().stream()
                            .collect(
                                    Collectors.toMap(
                                            Map.Entry::getKey,
                                            e -> new JsonArray(
                                                    e.getValue().stream().map(Pair::snd).collect(Collectors.toList())
                                            )
                                    )
                            )
                        ).reduce(new HashMap<>(),(a, b) -> {
                            b.putAll(a);
                            return b;
                        })
                ).toString()
        );
    }

    public RouterConfigAnnotation() {
        super(init());
    }
}