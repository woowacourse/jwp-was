package webserver;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.domain.QueryParameter;
import webserver.domain.Request;
import webserver.support.RequestHelper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestDispatcherTest extends RequestHelper {

    @Test
    @DisplayName("url 분기처리를 제대로 하는지 확인")
    void forward() throws IOException {
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