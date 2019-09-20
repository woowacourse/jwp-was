package http.response;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpResponseEntity {
    HttpResponse makeResponse() throws IOException, URISyntaxException;
}
