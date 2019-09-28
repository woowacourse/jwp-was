package http;

import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface ContentTypesSupplier extends Supplier<List<ContentType>> {
}