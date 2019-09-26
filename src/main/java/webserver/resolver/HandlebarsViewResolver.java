package webserver.resolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class HandlebarsViewResolver implements Resolver {

    @Override
    public byte[] resolve(String path) throws IOException, URISyntaxException {
        return new byte[0];
    }
}
