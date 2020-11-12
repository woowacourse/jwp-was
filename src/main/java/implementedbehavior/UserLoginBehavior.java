package implementedbehavior;

import db.DataBase;
import http.HttpBody;
import http.request.Params;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import model.User;
import webserver.requestmapping.behavior.RequestBehavior;

import java.util.Objects;

public class UserLoginBehavior implements RequestBehavior {
    @Override
    public void behave(RequestEntity requestEntity, ResponseEntity responseEntity) {
        HttpBody httpBody = requestEntity.getHttpBody();
        Params userInfo = Params.from(httpBody.getContent());
        User user = DataBase.findUserById(userInfo.findValueBy("userId"));
        if (!Objects.isNull(user) && (user.getPassword().equals(userInfo.findValueBy("password")))) {
            responseEntity.status(HttpStatus.FOUND)
                .addHeader("Location", "/index.html")
                .addHeader("Set-Cookie", "logined=true");
        } else {
            responseEntity.status(HttpStatus.FOUND)
                .addHeader("Location", "/user/login_failed.html")
                .addHeader("Set-Cookie", "logined=false");
        }
    }
}
