package http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private static Stream<Arguments> provideHttpRequest() {
        return Stream.of(
                Arguments.of("Http_GET.txt",
                        "http://localhost:8080/user/create",
                        "localhost:8080",
                        "aiden",
                        true),
                Arguments.of("Http_POST.txt",
                        "http://localhost:8080/user/create",
                        "localhost:8080",
                        "aiden",
                        false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideHttpRequest")
    public void requestHandlerTest(String file, String url, String host, String userId, boolean isGet) throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + file));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        RequestHandler requestHandler = new RequestHandler(br);
        HttpRequest httpRequest = requestHandler.create();

        assertThat(httpRequest.getUrl()).isEqualTo(url);
        assertThat(httpRequest.getHeader("Host")).isEqualTo(host);
        assertThat(httpRequest.getParameter("userId")).isEqualTo(userId);
        assertThat(httpRequest.isGet()).isEqualTo(isGet);
    }
}