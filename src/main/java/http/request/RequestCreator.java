package http.request;

import java.io.IOException;
import java.util.List;

public interface RequestCreator {
    Request create(List<String> lines, String[] tokens) throws IOException;
}
