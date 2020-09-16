package db;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import model.User;

class DataBaseTest {
    User user;
    User user1;
    User user2;

    @Test
    void create() {
        user = new User("ID", "TEST_PASSWORD", "TEST_NAME", "TEST_EMAIL");
        DataBase.addUser(user);

        assertThat(DataBase.findUserById("ID")).isEqualToComparingFieldByField(user);
    }

    @Test
    void findAll() {
        user = new User("ID", "TEST_PASSWORD", "TEST_NAME", "TEST_EMAIL");
        user1 = new User("ID1", "TEST_PASSWORD1", "TEST_NAME1", "TEST_EMAIL1");
        user2 = new User("ID2", "TEST_PASSWORD2", "TEST_NAME2", "TEST_EMAIL2");
        DataBase.addUser(user);
        DataBase.addUser(user1);
        DataBase.addUser(user2);

        assertThat(DataBase.findAll()).containsExactlyInAnyOrderElementsOf(Arrays.asList(user, user1, user2));
    }
}