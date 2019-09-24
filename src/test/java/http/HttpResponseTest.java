package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.support.PathHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponseTest {
    private static final Logger log = LoggerFactory.getLogger(HttpResponseTest.class);

    @Test
    @DisplayName("정적 파일 Forwarding")
    public void HtmlPageForwarding() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = createOutPutStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        httpResponse.addHeader("Content-Type", "text/html");
        httpResponse.forward(PathHandler.path("/index.html"));
        log.info("{}", byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Redirect")
    public void Redirect() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = createOutPutStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        httpResponse.addHeader("Content-Type", "text/html");
        httpResponse.addHeader("Location", "/index.html");
        httpResponse.sendRedirect();
        log.info("{}", byteArrayOutputStream.toString());
    }

    private ByteArrayOutputStream createOutPutStream() {
        return new ByteArrayOutputStream();
    }
}
