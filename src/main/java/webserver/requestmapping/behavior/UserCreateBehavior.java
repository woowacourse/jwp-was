package webserver.requestmapping.behavior;

import db.DataBase;
import http.HttpBody;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import model.User;
import stringprocessor.Params;

public class UserCreateBehavior implements RequestBehavior {
    @Override
    public ResponseEntity behave(RequestEntity requestEntity) {
        HttpBody httpBody = requestEntity.getHttpBody();
        Params userInfo = Params.from(httpBody.getContent());
        User user = new User(
            userInfo.findValueBy("userId"),
            userInfo.findValueBy("password"),
            userInfo.findValueBy("name"),
            userInfo.findValueBy("email"));
        DataBase.addUser(user);

        return ResponseEntity.status(HttpStatus.FOUND)
            .version("HTTP/1.1")
            .addHeader("Location", "/index.html");
    }
}
