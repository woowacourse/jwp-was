package server.web.controller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Handlers implements Iterable<Handler> {
    private final List<Handler> handlers;

    public Handlers(List<String> sourcePaths) {
        this.handlers = sourcePaths.stream()
                .filter(Handlers::isController)
                .map(Handler::of)
                .collect(Collectors.toList());
    }

    private static boolean isController(String sourcePath) {
        Class<?> maybeController = null;
        try {
            maybeController = Class.forName(sourcePath);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("존재하지 않는 클래스 파일입니다.");
        }

        boolean isControllerType = Arrays.stream(maybeController.getInterfaces())
                .anyMatch(aInterface -> aInterface.getName().equals(Controller.class.getName()));
        RequestMapping annotation = maybeController.getAnnotation(RequestMapping.class);

        return isControllerType && Objects.nonNull(annotation);
    }

    @Override
    public Iterator<Handler> iterator() {
        return handlers.iterator();
    }

    @Override
    public void forEach(Consumer<? super Handler> action) {
        handlers.forEach(action);
    }

    @Override
    public Spliterator<Handler> spliterator() {
        return handlers.spliterator();
    }
}
