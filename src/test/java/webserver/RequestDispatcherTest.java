package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.message.HttpStatus;
import webserver.message.HttpVersion;
import webserver.message.response.Response;
import webserver.support.RequestHelper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestDispatcherTest extends RequestHelper {

    @Test
    @DisplayName("url 분기처리를 제대로 하는지 확인")
    void forwardIndex() {
        final String actual = new String(RequestDispatcher.forward(ioUtils(requestPostWithQuery)));

        Response response = new Response(HttpVersion.HTTP_1_1);
        response.redirect("/");

        assertThat(actual).contains("302 Found");
        assertThat(actual).contains("Location: /");
    }

    @Test
    @DisplayName("url 분기처리를 제대로 하는지 확인")
    void forwardIndex2() throws IOException, URISyntaxException {
        final byte[] actual = RequestDispatcher.forward(ioUtils(requestGetHeader));

        final StaticFile staticFile = new StaticFile("./templates/index.html");
        final Response response = new Response(HttpVersion.HTTP_1_1);
        response.body(staticFile);

        assertThat(actual).isEqualTo(response.toBytes());
    }

    @Test
    @DisplayName("존재하지 않는 URL로 접근하였을 경우 처리")
    void forwardNotFound() throws IOException, URISyntaxException {
        final byte[] actual = RequestDispatcher.forward(ioUtils(requestNotFoundHeader));

        final StaticFile staticFile = new StaticFile("./templates/error/404_not_found.html");
        final Response response = new Response(HttpVersion.HTTP_1_1);
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.body(staticFile);

        assertThat(actual).isEqualTo(response.toBytes());
    }

    @Test
    @DisplayName("서버 오류 시 500 페이지가 출력되는지 확인")
    void forwardInternalServerError() throws IOException, URISyntaxException {
        final byte[] actual = RequestDispatcher.forward(null);

        final StaticFile staticFile = new StaticFile("./templates/error/500_internal_error.html");
        final Response response = new Response(HttpVersion.HTTP_1_1);
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.body(staticFile);

        assertThat(actual).isEqualTo(response.toBytes());
    }
}