package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.message.HttpStatus;
import webserver.message.response.Response;
import webserver.support.RequestHelper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestDispatcherTest extends RequestHelper {

    @Test
    @DisplayName("url 분기처리를 제대로 하는지 확인")
    void forwardIndex() throws IOException, URISyntaxException {
        final byte[] response = RequestDispatcher.forward(ioUtils(requestGetHeader));
        final StaticFile staticFile = new StaticFile("./templates/index.html");

        assertThat(response).isEqualTo(new Response.Builder()
                .httpStatus(HttpStatus.OK)
                .body(staticFile)
                .build().toBytes());
    }

    @Test
    @DisplayName("존재하지 않는 URL로 접근하였을 경우 처리")
    void forwardNotFound() throws IOException, URISyntaxException {
        final byte[] response = RequestDispatcher.forward(ioUtils(requestNotFoundHeader));
        final StaticFile staticFile = new StaticFile("./templates/error/404_not_found.html");

        assertThat(response).isEqualTo(new Response.Builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .body(staticFile)
                .build().toBytes());
    }

    @Test
    @DisplayName("서버 오류 시 500 페이지가 출력되는지 확인")
    void forwardInternalServerError() throws IOException, URISyntaxException {
        final byte[] response = RequestDispatcher.forward(null);
        final StaticFile staticFile = new StaticFile("./templates/error/500_internal_error.html");

        assertThat(response).isEqualTo(new Response.Builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(staticFile)
                .build().toBytes());
    }
}