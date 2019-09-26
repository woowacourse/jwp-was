package webserver.resolver;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ViewResolver {
    byte[] resolve(String path) throws IOException, URISyntaxException;
}
