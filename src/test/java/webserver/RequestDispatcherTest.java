package webserver;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.QueryParameter;
import webserver.domain.Request;
import webserver.view.NetworkInput;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestDispatcherTest {
    // TODO 중복 제거
    private final String requestInput =
            "GET /index.html HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";
    private final String requestInputWithQueryString =
            "GET /user/create" +
                    "?userId=javajigi&password=password" +
                    "&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                    "Host: localhost:8080\n" +
                    "Connection: keep-alive\n" +
                    "Accept: */*";
    private InputStream inputStream;
    private InputStream inputStreamWithQueryString;
    private NetworkInput networkInput;
    private NetworkInput networkInputWithQueryString;

    @BeforeEach
    void setUp() {
        inputStream = new ByteArrayInputStream(requestInput.getBytes());
        inputStreamWithQueryString = new ByteArrayInputStream(requestInputWithQueryString.getBytes());
        networkInput = new NetworkInput(inputStream);
        networkInputWithQueryString = new NetworkInput(inputStreamWithQueryString);
    }

    @Test
    @DisplayName("url 분기처리를 제대로 하는지 확인")
    void forward() {
        final Request request = new Request(networkInputWithQueryString);

        final QueryParameter queries = request.getQueryParameter();
        final String userId = queries.getValue("userId");
        final String password = queries.getValue("password");
        final String name = queries.getValue("name");
        final String email = queries.getValue("email");
        final User user = new User(userId, password, name, email);

        final byte[] response = RequestDispatcher.forward(request);
        assertThat(response).isEqualTo(user.toString().getBytes());
    }
}