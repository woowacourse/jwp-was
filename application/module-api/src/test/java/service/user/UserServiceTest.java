package service.user;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import user.User;
import user.UserRepository;

class UserServiceTest {
    private final UserService userService = UserService.getInstance();

    @BeforeEach
    void setUp() {
        UserRepository.clear();
    }

    @Test
    void addUser() {
        User user = new User("userId", "password", "name", "email");

        userService.addUser(user);

        assertThat(UserRepository.findAll()).hasSize(1);
    }

    @Test
    void findByUserId() {
        User user = new User("userId", "password", "name", "email");
        userService.addUser(user);

        User result = userService.findByUserId("userId");

        assertThat(result).usingRecursiveComparison()
            .isEqualTo(user);
    }

    @Test
    void list() {
        User user = new User("userId", "password", "name", "email");
        userService.addUser(user);

        List<User> list = userService.list();

        assertThat(list).containsExactly(user);
    }
}
