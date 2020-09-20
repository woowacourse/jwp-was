package webserver;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;
import webserver.messageconverter.DefaultHttpMessageConverter;

class DefaultHttpMessageConverterTest {
    public static final String ID = "javajigi";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "javajigi%40slipp.net";
    public static final String NAME = "kim";

    @DisplayName("정상적으로 User Class로 데이터를 converting 합니다.")
    @Test
    void convert() {
        DefaultHttpMessageConverter converter = new DefaultHttpMessageConverter();
        Map<String, String> body = new HashMap<>();
        body.put("userId", ID);
        body.put("password", PASSWORD);
        body.put("email", EMAIL);
        body.put("name", NAME);

        User user = converter.convert(User.class, body);
        assertThat(user).isEqualToComparingFieldByField(new User(ID, PASSWORD, NAME, EMAIL));
    }

    @Test
    void isSupport() {
        DefaultHttpMessageConverter converter = new DefaultHttpMessageConverter();
        Map<String, String> body = new HashMap<>();
        body.put("userId", ID);

        assertThat(converter.isSupport(User.class, body)).isTrue();
    }

    @Test
    void isNotSupport() {
        DefaultHttpMessageConverter converter = new DefaultHttpMessageConverter();
        Map<String, String> body = new HashMap<>();
        body.put("NONE_EXIST", ID);

        assertThat(converter.isSupport(User.class, body)).isFalse();
    }
}
