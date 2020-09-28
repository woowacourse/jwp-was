package utils;

import domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserMapperTest {
    private String testDirectory = "./src/test/resources/";

    @DisplayName("input data에서 User 객체로 변환")
    @Test
    void createUser() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        User actual = UserMapper.createUser(new HttpRequest(in));
        User expected = new User("javajigi", "password", "JaeSung", null);

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }


}