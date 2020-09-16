package utils;

import java.util.function.Supplier;

public enum ModelType {
    USER(UserExtractor::new);

    private final Supplier<ModelObjectExtractor> supplier;

    ModelType(Supplier<ModelObjectExtractor> supplier) {
        this.supplier = supplier;
    }

    public <T> T getModel(String body) {
        return supplier.get().extract(body);
    }
}
