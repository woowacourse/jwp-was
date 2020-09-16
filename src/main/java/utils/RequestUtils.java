package utils;

import domain.user.User;
import domain.user.dto.UserRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class RequestUtils {
    private static final String INPUT_DELIMITER = "&";

    public static User createUser(String input) {
        input = decode(input);
        UserRequest userRequest = new UserRequest();
        validateInputData(input, userRequest);
        // 정규식으로 바꾸는게 나을까?
        for (String userField : userRequest.getFieldNames()) {
            int fromIndex = calculateFromIndexOfValue(input, userField);
            boolean isLastValue = userRequest.isLastField(userField);
            int toIndex = calculateToIndexOfValue(input, fromIndex, isLastValue);
            String inputValue = input.substring(fromIndex, toIndex);
            userRequest.set(userField, inputValue);
        }
        return userRequest.toUser();
    }

    private static void validateInputData(String input, UserRequest userRequest) {
        if (!input.matches(userRequest.getRegex())) {
            throw new IllegalArgumentException("올바르지 않은 형식의 input data: " + input);
        }
    }

    private static int calculateToIndexOfValue(String input, int fromIndex, boolean isLastValue) {
        if (isLastValue) {
            return input.length();
        }
        return input.indexOf(INPUT_DELIMITER, fromIndex);
    }

    private static int calculateFromIndexOfValue(String input, String field) {
        return input.indexOf(field) + field.length() + 1;
    }

    private static String decode(String data) {
        try {
            return URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException" + data);
        }
    }
}
