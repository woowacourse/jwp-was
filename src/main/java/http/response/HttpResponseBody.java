package http.response;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponseBody {
    private byte[] body;

    public HttpResponseBody(String uri) throws IOException, URISyntaxException {
        this.body = FileIoUtils.loadFileFromClasspath("./templates/" + uri);
    }

    public byte[] getBody() {
        return body;
    }

    public int getBodyLength() {
        return body.length;
    }
}
