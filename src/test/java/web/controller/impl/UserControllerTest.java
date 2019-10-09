package web.controller.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.controller.Controller;
import webserver.message.HttpVersion;
import webserver.message.request.Request;
import webserver.message.response.Response;
import webserver.support.RequestHelper;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest extends RequestHelper {
    @Test
    @DisplayName("회원가입 테스트")
    void name() throws IOException, URISyntaxException {
        // given
        Request request = new Request(ioUtils(requestPostWithUserEnrollQuery));
        Response response = new Response(HttpVersion.HTTP_1_1);

        Controller userController = new UserController();

        // when
        userController.service(request, response);
        String actual = new String(response.toBytes());
        System.out.println(actual);

        // then
        assertThat(actual).contains("302 Found");
        assertThat(actual).contains("Location: /");
    }
}