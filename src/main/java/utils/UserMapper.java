package utils;

import domain.user.User;
import domain.user.dto.UserRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class UserMapper {

    public static User createUser(Map<String, String> inputs) {
        UserRequest userRequest = new UserRequest();
        for (String key : inputs.keySet()) {
            String value = inputs.get(key);
            userRequest.set(key, decode(value));
        }
        return userRequest.toUser();
    }

    private static String decode(String data) {
        try {
            return URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException" + data);
        }
    }
}
