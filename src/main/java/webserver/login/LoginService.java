package webserver.login;

import db.DataBase;
import model.User;

public class LoginService {

    public boolean login(String id, String password) {
        User user = DataBase.findUserById(id);
        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(password)) {
            return false;
        }
        return true;
    }
}
