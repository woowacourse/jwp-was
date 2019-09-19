package utils.io;

import utils.recursion.Done;
import utils.recursion.TailCall;
import utils.recursion.TailRecursion;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface NetworkIO extends Iterator<String>, AutoCloseable {
    default boolean isEOF() {
        return !hasNext();
    }

    @Override
    default String next() {
        return readLine();
    }

    String readLine();

    default String readWhile(Predicate<? super String> condition) {
        class AnonymousPlaceholder {}
        return new AnonymousPlaceholder() {
            BiFunction<Predicate<? super String>, StringBuilder, TailRecursion<String>> f = (cond, acc) -> {
                if (!isEOF()) {
                    final String line = readLine();
                    acc.append(line);
                    acc.append("\n");
                    if (condition.test(line)) {
                        return (TailCall<String>) () -> this.f.apply(cond, acc);
                    }
                }
                return (Done<String>) acc::toString;
            };
        }.f.apply(condition, new StringBuilder()).get();
    }

    void write(byte[] body);

    default void write(String body) {
        write(body.getBytes());
    }
}