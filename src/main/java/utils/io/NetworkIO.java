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
                final String line = this.appendLine(acc);
                return (!line.isEmpty() && condition.test(line))
                        ? (TailCall<String>) () -> this.readLinesWhile(acc)
                        : (Done<String>) acc::toString;
            }

            private String appendLine(StringBuilder acc) {
                if (!isEOF()) {
                    final String line = readLine();
                    acc.append(line).append("\r\n");
                    return line;
                }
                return "";
            }
        }
        return (new Closure()).readLinesWhile(new StringBuilder()).get();
    }

    String readAllLeft();

    void write(String body);

    void close();
}