package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.message.HttpStatus;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.support.RequestHelper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestDispatcherTest extends RequestHelper {

    /*@Test
    @DisplayName("url 분기처리를 제대로 하는지 확인")
    void forward() throws IOException {
        final Request request = new Request(ioUtils);
        final Response response = RequestDispatcher.forward(request);
        assertThat(response.toBytes())
                .isEqualTo(new Response.Builder()
                        .redirectUrl("/index.html")
                        .body("")
                        .build().toBytes());
    }*/
}