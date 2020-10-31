package web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.http.HttpRequest;
import web.http.HttpResponse;
import web.session.Cookie;
import web.session.Session;
import web.session.SessionFactory;
import web.session.SessionStorage;
import webserver.RequestMapping;

public class UserListControllerTest {

    private String testDirectory = "./src/test/resources/";

    @DisplayName("로그인 후 사용자 목록 조회")
    @Test
    void listTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_USER_LIST.txt"));
        HttpRequest request = HttpRequest.from(in);
        HttpResponse response = new HttpResponse(createOutputStream("USER_LIST.txt"));

        Session session = SessionFactory.getNewSession();
        SessionStorage.add(session);
        request.addHeader(createHeader(session.getId()));

        Controller controller = RequestMapping.getController("/user/list.html");
        controller.service(request, response);
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }

    private Map<String, Object> createHeader(String sessionId) {
        Map<String, Object> headers = new HashMap<>();
        Cookie cookie = new Cookie();
        cookie.add("logined", sessionId);
        headers.put("Cookie", cookie);

        return headers;
    }
}
