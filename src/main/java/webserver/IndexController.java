package webserver;

import java.io.IOException;
import java.net.URISyntaxException;

public class IndexController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse)
        throws IOException, URISyntaxException {
        httpResponse.forward(httpRequest.getPath());
    }
}
