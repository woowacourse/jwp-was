package http.request;

import java.net.URI;
import java.net.URISyntaxException;

public class HttpUriParser {

    public static HttpUri parse(String uri) throws URISyntaxException {
        return new HttpUri(new URI(uri));
    }
}
