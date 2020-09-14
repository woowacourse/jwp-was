package webserver.requestmapping.behavior;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import db.DataBase;
import http.request.RequestBody;
import http.request.RequestEntity;
import model.User;
import stringprocessor.Params;

public class UserCreateBehavior implements RequestBehavior {
    @Override
    public InputStream behave(RequestEntity requestEntity) {
        RequestBody requestBody = requestEntity.getRequestBody();
        Params userInfo = Params.from(requestBody.getContent());
        User user = new User(
            userInfo.findValueBy("userId"),
            userInfo.findValueBy("password"),
            userInfo.findValueBy("name"),
            userInfo.findValueBy("email"));
        DataBase.addUser(user);

        String response = "HTTP/1.1 302 FOUND \r\n" +
            "Location: /index.html \r\n" +
            "\r\n";
        return new ByteArrayInputStream(response.getBytes());
    }
}
