package webserver.router;

import controller.IndexController;
import controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.fp.tuple.Pair;
import utils.fp.tuple.Triplet;
import webserver.httpelement.HttpMethod;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RouterConfigAnnotation extends RouterConfig {
    private static final Logger logger = LoggerFactory.getLogger(RouterConfigAnnotation.class);

    protected static RouterConfigAnnotation init() {
        return new RouterConfigAnnotation(
                scanDestinationMethods().map(dest -> {
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
                                            y -> new MappedDestination(y.snd(), Collections.emptyMap())
                                    )
                            )
                    )
                ).collect(Collectors.toMap(Pair::fst, x -> new Pathfinder(x.snd())))
        );
    }

    private static Stream<Method> scanDestinationMethods() {
        return scanAllClasses().flatMap(clazz ->
                Stream.of(clazz.getMethods()).filter(method -> method.isAnnotationPresent(RequestMapping.class))
        );
    }

    /* 나중에 구현 .... */
    private static Stream<Class<?>> scanAllClasses() {
        return Stream.of(IndexController.class, UserController.class);
    }

    private RouterConfigAnnotation(Map<HttpMethod, Pathfinder> matcher) {
        super(matcher);
    }
}