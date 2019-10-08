package web.controller.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.message.request.Request;
import webserver.support.RequestHelper;

import java.io.IOException;
import java.net.URISyntaxException;

class UserControllerTest extends RequestHelper {
    @Test
    @DisplayName("회원가입 테스트")
    void name() throws IOException, URISyntaxException {
        // given
        Request request = new Request(ioUtils(requestPostWithUserEnrollQuery));
        UserController userController = new UserController();

        // when
        /*Response response = userController.doPost(request);

        // then
        assertThat(new String(response.toBytes()))
                .isEqualTo(new String(new ResponseBuilder().redirectUrl("/").build().toBytes()));*/
    }
}