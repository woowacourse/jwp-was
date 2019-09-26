package http.response;

import http.request.HttpRequest;
import http.request.RequestHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHandlerTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    @Test
    @DisplayName("Response 생성 테스트")
    public void createTest() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        RequestHandler requestHandler = new RequestHandler(br);
        HttpRequest httpRequest = requestHandler.create();
        HttpResponse httpResponse = ResponseHandler.create(httpRequest);

        assertThat(httpResponse).isInstanceOf(HttpResponse.class);
    }
}