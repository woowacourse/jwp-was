package utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

public interface NetworkIO extends Iterator<Optional<String>>, AutoCloseable {
    @Override
    default Optional<String> next() {
        return readLine();
    }

    Optional<String> readLine();
    void write(String body) throws IOException;
}