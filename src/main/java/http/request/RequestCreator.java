package http.request;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
public interface RequestCreator {
    Request create(BufferedReader br, String[] tokens) throws IOException;
}
