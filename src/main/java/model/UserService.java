package model;

import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void addUser(final User user) {
        DataBase.addUser(user);
        logger.debug("insert user : {}", user);
        logger.debug("user list : {}", DataBase.findAll());
    }

    // @TODO 리팩토링
    public boolean login(final User loginUser) {
        User other;

        try {
            // @TODO 사용자 지정 Exception 고려
            other = Optional.ofNullable(DataBase.findUserById(loginUser.getUserId()))
                    .orElseThrow(NoSuchElementException::new);
            return loginUser.match(other);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public Collection<User> findAll() {
        return DataBase.findAll();
    }
}
