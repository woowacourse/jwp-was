package webserver.controller;

import db.DataBase;
import http.ContentType;
import http.parameter.ParameterParser;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        if (request.hasParameters()) {
            DataBase.addUser(createUser(request));

            // 기존에 존재하던 파일을 제공 할 것인지..
            // 그렇다면... 우예할까??
            // 일단은 static, templates 에 존재하는 파일을 요청하는 부분에 집중해볼까??
            response.response200Header(0, ContentType.fromMimeType("text/html").get());
            return;
        }
        String stringOfUtf_8 = "안녕";
        byte[] b = stringOfUtf_8.getBytes(StandardCharsets.UTF_8);
        log.debug("length: {}", b.length);
        response.response200Header(b.length, ContentType.TXT);
        response.responseBody(b);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws UnsupportedEncodingException {
        if (request.hasBody()) {
            String body = request.getBody().toString();
            body = URLDecoder.decode(body, "UTF-8");
            Map<String, String> bodyData = ParameterParser.parse(body);

            DataBase.addUser(createUser(bodyData));

            // response 만들기
            response.response302Header("/index.html");

        }
    }

    private User createUser(HttpRequest request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        return new User(userId, password, name, email);
    }

    private User createUser(Map<String, String> bodyData) {
        String userId = bodyData.get("userId");
        String password = bodyData.get("password");
        String name = bodyData.get("name");
        String email = bodyData.get("email");

        return new User(userId, password, name, email);
    }
}
