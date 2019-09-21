package utils.io;

import utils.recursion.Done;
import utils.recursion.TailCall;
import utils.recursion.TailRecursion;

import java.util.Iterator;
import java.util.function.Predicate;

public interface NetworkIO extends Iterator<String> {
    default boolean isEOF() {
        return !hasNext();
    }

    @Override
    default String next() {
        return readLine();
    }

    String readLine();

    default String readLinesWhile(Predicate<? super String> condition) {
        class Closure {
            private TailRecursion<String> readLinesWhile(StringBuilder acc) {
                final String line = readLine();
                if (line.isEmpty()) {
                    return (Done<String>) acc::toString;
                }
                return condition.test(line)
                        ? (TailCall<String>) () -> this.readLinesWhile(acc.append(line))
                        : (Done<String>) () -> acc.append(line).toString();
            }
        }
        return (new Closure()).readLinesWhile(new StringBuilder()).get();
    }

    default String readLinesWhileNotEmpty() {
        return readLinesWhile(line -> !line.replaceAll("(\r|\n|\r\n)", "").isEmpty());
    }

    String readAllLeft();

    void write(String body);

    void close();
}