package webserver.env;

import utils.fp.tuple.Pair;
import utils.fp.tuple.Triplet;

import java.util.stream.Collectors;

public final class RouterConfigAnnotation extends RouterConfig {
    public RouterConfigAnnotation() {
        super(
                Env.reflections().getAllMethodsAnnotatedX(RequestMapping.class).map(dest -> {
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
                )
        );
    }
}