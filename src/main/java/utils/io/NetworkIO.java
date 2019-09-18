package utils;

import java.io.IOException;
import java.util.Iterator;

public interface NetworkIO extends Iterator<String>, AutoCloseable {
    @Override
    default String next() {
        return readLine();
    }

    String readLine();
    void write(String body) throws IOException;
}