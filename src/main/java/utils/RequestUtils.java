package utils;

import model.User;
import model.UserRequest;

public class RequestUtils {
    private static final String QUERY_STRING_DELIMITER = "?";
    private static final int MIN_INDEX = 0;
    private static final String INPUT_DELIMITER = "&";

    public static String extractQueryString(String resource) {
        int startIndex = resource.indexOf(QUERY_STRING_DELIMITER);
        if (startIndex < MIN_INDEX) {
            throw new RuntimeException("QueryString을 포함하고 있지 않습니다.");
        }
        return resource.substring(startIndex) + 1;
    }

    public static User createUser(String input) {
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
            throw new IllegalArgumentException("올바른 형식의 input 데이터가 아닙니다.");
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
}
