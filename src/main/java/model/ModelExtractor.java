package model;

import java.util.Map;
import java.util.function.Function;

public enum ModelExtractor {
    USER(User::new);

    private final Function<Map<String, String>, Model> function;

    ModelExtractor(Function<Map<String, String>, Model> function) {
        this.function = function;
    }

    public Model getModel(Map<String, String> parameter) {
        return function.apply(parameter);
    }
}
