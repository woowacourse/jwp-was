package controller;

import http.common.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.net.HttpHeaders.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.BLANK;

class UserListControllerTest extends AbstractControllerTest {
    private UserListController userListController = UserListController.getInstance();
    private User signUpUser = new User("signUpId", "password", "name", "email");

    @BeforeEach
    void setUp() {
        signUp(signUpUser);
    }

    @Test
    void 로그인후_요청시_200_정상_응답() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET /user/list HTTP/1.1"),
                getLoginHttpHeader(signUpUser.getUserId(), signUpUser.getPassword()),
                new RequestBody(BLANK, BLANK));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        userListController.doGet(httpRequest, httpResponse);
        String responseBody = new String(httpResponse.getBody());

        assertEquals(httpResponse.getResponseStatus(), ResponseStatus.OK);
        assertThat(responseBody).contains(signUpUser.getUserId());
        assertThat(responseBody).contains(signUpUser.getName());
        assertThat(responseBody).contains(signUpUser.getEmail());
    }

    @Test
    void 로그아웃_상태로_요청시_302_응답() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET /user/list HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(BLANK, BLANK));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        userListController.doGet(httpRequest, httpResponse);

        assertEquals(httpResponse.getResponseStatus(), ResponseStatus.FOUND);
        assertEquals(httpResponse.getHttpHeader().getHeaderAttribute(LOCATION), "/");
    }

}