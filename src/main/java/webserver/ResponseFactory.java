package webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import utils.RequestUtils;

public class ResponseFactory {
    public static void response(OutputStream out, String path, byte[] body) throws IOException, URISyntaxException {
        HttpResponse httpResponse;
        if (body != null) {
            httpResponse = new HttpResponse(HttpStatus.OK, out);
            httpResponse.addHeader("Content-Type",
                String.format("text/%s;charset=utf-8", RequestUtils.extractExtension(path)));
            httpResponse.addHeader("Content-Length", String.valueOf(body.length));
            httpResponse.forward(path);
        }
        if (body == null) {
            httpResponse = new HttpResponse(HttpStatus.FOUND, out);
            httpResponse.sendRedirect("/index.html");
        }
    }
}
