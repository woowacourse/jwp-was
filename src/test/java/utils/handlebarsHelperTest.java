package utils;

import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class handlebarsHelperTest {

    @Test
    void umm() throws IOException {
        List<User> users = Arrays.asList(
                new User("1", "1", "1", "1"),
                new User("1", "1", "1", "1"),
                new User("1", "1", "1", "1")
        );
        System.out.println(HandlebarsHelper.umm(users));
    }
}