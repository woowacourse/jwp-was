package utils;

import domain.user.User;
import domain.user.dto.UserRequest;
import webserver.request.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UserMapper {

    public static User createUser(HttpRequest httpRequest) {
        UserRequest userRequest = new UserRequest();
        for (String key : httpRequest.getParametersKeys()) {
            String value = httpRequest.getParameter(key);
            userRequest.set(key, decode(value));
        }
        return userRequest.toUser();
    }

    private static String decode(String data) {
        try {
            return URLDecoder.decode(data, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException" + data);
        }
    }
}
