package application.web;

import application.db.DataBase;
import http.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static http.HttpRequestTest.NEW_LINE;
import static org.assertj.core.api.Assertions.assertThat;

class UserServletTest {

    @DisplayName("http body에 정보를 담아서 post 요청을 보내면 User 클래스를 생성해서 DB에 저장한다.")
    @Test
    void doPost() throws IOException {
        // given '/user/create'로 요청이 있다.
        String httpRequestInput = "POST /user/create HTTP/1.1" + NEW_LINE
                + "Content-Type: text/html;charset=UTF-8" + NEW_LINE
                + "Content-Length: 93" + NEW_LINE
                + "Accept-Language: en-US,en;q=0.9" + NEW_LINE
                + NEW_LINE
                + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net" + NEW_LINE;

        Reader inputString = new StringReader(httpRequestInput);
        BufferedReader br = new BufferedReader(inputString);

        HttpRequest httpRequest = new HttpRequest(br);

        int before = DataBase.findAll().size();

        // when UserServlet이 해당 요청을 처리한다.
        UserServlet userServlet = new UserServlet();
        userServlet.doPost(httpRequest);

        // then
        int after = DataBase.findAll().size();
        assertThat(before + 1).isEqualTo(after);
    }
}