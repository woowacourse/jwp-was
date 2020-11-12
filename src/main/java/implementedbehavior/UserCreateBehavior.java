package implementedbehavior;

import db.DataBase;
import http.HttpBody;
import http.request.Params;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import model.User;
import webserver.requestmapping.behavior.RequestBehavior;

public class UserCreateBehavior implements RequestBehavior {
    @Override
    public void behave(RequestEntity requestEntity, ResponseEntity responseEntity) {
        HttpBody httpBody = requestEntity.getHttpBody();
        Params userInfo = Params.from(httpBody.getContent());
        User user = new User(
            userInfo.findValueBy("userId"),
            userInfo.findValueBy("password"),
            userInfo.findValueBy("name"),
            userInfo.findValueBy("email"));
        DataBase.addUser(user);

       responseEntity.status(HttpStatus.FOUND)
            .addHeader("Location", "/index.html");
    }
}
