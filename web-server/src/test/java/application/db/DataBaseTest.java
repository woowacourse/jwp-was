package application.db;

import static org.assertj.core.api.Assertions.assertThat;

import application.controller.WrongUserIdPasswordException;
import application.model.User;
import org.junit.jupiter.api.Test;

class DataBaseTest {

    @Test
    void addAndFindUser() throws WrongUserIdPasswordException {
        DataBase.addUser(new User("id", "password", "name", "email"));

        User user = DataBase.findUserById("id");

        assertThat(user.getUserId()).isEqualTo("id");
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    void findAll() {
        DataBase.addUser(new User("id1", "password1", "name1", "email1"));
        DataBase.addUser(new User("id2", "password2", "name2", "email2"));

        assertThat(DataBase.findAll()).hasSize(2);
    }
}
