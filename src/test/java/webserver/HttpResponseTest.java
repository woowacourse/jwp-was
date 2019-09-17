package webserver;

import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.*;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {

    @Test
    void index문서_get요청_response_body_확인() throws IOException, URISyntaxException {
        String request = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest(in);
        HttpResponse httpResponse = new HttpResponse(out);
        httpResponse.addBody(httpRequest);

        assertThat(out.toByteArray()).isEqualTo(FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getPath()));
    }

}