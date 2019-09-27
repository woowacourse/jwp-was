package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.view.ViewResolverTest;
import webserver.support.PathHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponseTest {
    private static final Logger log = LoggerFactory.getLogger(HttpResponseTest.class);

    @Test
    @DisplayName("정적 파일 Forwarding")
    public void StaticResourceForwarding() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = createOutPutStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        httpResponse.addHeader("Content-Type", "text/html");
        httpResponse.forward(PathHandler.path("/index.html"));
        log.info("{}", byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("/profile/html에 대한 Forwarding을 수행한다")
    public void templateResourceForwarding() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = createOutPutStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        httpResponse.addHeader("Content-Type", "text/html");
        httpResponse.forward(ViewResolverTest.resolve());
        log.info("{}", byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("Redirect")
    public void sendRedirect() throws IOException {
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
