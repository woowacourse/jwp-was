package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest extends BaseTest {

    @DisplayName("HttpRequest 생성")
    @Test
    void of() throws IOException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes()));
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(GET_REQUEST_MESSAGE.getBytes())));
        RequestStatusLine requestStatusLine = RequestStatusLine.of(br);
        RequestHeader requestHeader = RequestHeader.of(br);
        RequestBody requestBody = RequestBody.of(br, requestHeader.getContentLength());

        assertThat(httpRequest.getRequestStatusLine()).isEqualTo(requestStatusLine);
        assertThat(httpRequest.getRequestHeader()).isEqualTo(requestHeader);
        assertThat(httpRequest.getRequestBody()).isEqualTo(requestBody);
    }

    @DisplayName("query string에 데이터가 있는 경우에 요청 메시지의 데이터를 조회하는지 확인")
    @Test
    void getParametersInQueryString() throws IOException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(GET_REQUEST_MESSAGE_WITH_QUERY_STRING.getBytes()));
        assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getParameter("name")).isEqualTo("박재성");
        assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net");
    }

    @DisplayName("요청 메시지의 바디에 데이터가 있는 경우에 요청 메시지의 데이터를 조회하는지 확인")
    @Test
    void getParametersInRequestBody() throws IOException {
        HttpRequest httpRequest = HttpRequest.of(new ByteArrayInputStream(POST_REQUEST_MESSAGE.getBytes()));
        assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
        assertThat(httpRequest.getParameter("name")).isEqualTo("박재성");
        assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net");
    }
}
