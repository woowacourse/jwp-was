package webserver.requestmapping.behavior;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import db.DataBase;
import http.request.RequestBody;
import http.request.RequestEntity;
import model.User;
import utils.StringUtils;

public class UserCreateBehavior implements RequestBehavior {
    @Override
    public InputStream behave(RequestEntity requestEntity) {
        RequestBody requestBody = requestEntity.getRequestBody();
        Map<String, String> userInfo = StringUtils.extractParams(requestBody.getContent());
        User user = new User(
            userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"), userInfo.get("email"));
        DataBase.addUser(user);

        String response = "HTTP/1.1 302 FOUND \r\n" +
            "Location: /index.html \r\n" +
            "\r\n";
        return new ByteArrayInputStream(response.getBytes());
    }
}
