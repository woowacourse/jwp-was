package http.request;

import exception.InvalidUriException;

import java.net.URI;
import java.net.URISyntaxException;

public class HttpUriParser {

    public static HttpUri parse(String uri)  {
        try {
            return new HttpUri(new URI(uri));
        } catch (URISyntaxException e) {
            throw new InvalidUriException();
        }
    }
}
