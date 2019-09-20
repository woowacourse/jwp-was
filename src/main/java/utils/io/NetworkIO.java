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

    default String readWhile(Predicate<? super String> condition) {
        class Closure {
            private TailRecursion<String> readWhile(StringBuilder acc) {
                final String line = this.appendLine(acc);
                if (line != null && condition.test(line)) {
                    return (TailCall<String>) () -> this.readWhile(acc);
                }
                return (Done<String>) acc::toString;
            }

            private String appendLine(StringBuilder acc) {
                if (!isEOF()) {
                    final String line = readLine();
                    acc.append(line);
                    acc.append("\r\n");
                    return line;
                }
                return null;
            }
        }
        return (new Closure()).readWhile(new StringBuilder()).get();
    }

    void write(String body);
}