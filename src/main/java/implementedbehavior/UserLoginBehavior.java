package implementedbehavior;

import db.DataBase;
import http.request.Params;
import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import http.session.HttpSession;
import http.session.HttpSessionStorage;
import model.User;
import webserver.requestmapping.behavior.RequestBehavior;

import java.util.Objects;

public class UserLoginBehavior implements RequestBehavior {
    @Override
    public void behave(RequestEntity requestEntity, ResponseEntity responseEntity) {
        Params userInfo = Params.from(requestEntity.getHttpBody().getContent());
        if (isValid(userInfo)) {
            HttpSession session = HttpSessionStorage.generate(userInfo.findValueBy("userId"));
            responseEntity.status(HttpStatus.FOUND)
                .addHeader("Location", "/index.html")
                .addHeader("Set-Cookie", session.toString());
        } else {
            responseEntity.status(HttpStatus.FOUND)
                .addHeader("Location", "/user/login_failed.html");
        }
    }

    private boolean isValid(Params userInfo) {
        String userId = userInfo.findValueBy("userId");
        String password = userInfo.findValueBy("password");
        User user = DataBase.findUserById(userId);
        return Objects.nonNull(user) && user.hasPasswordOf(password);
    }
}
