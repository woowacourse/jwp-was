package web;

public class UrlParameter {
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int MIN_LENGTH = 1;

    private final String key;
    private final String value;

    public UrlParameter(String param) {
        String[] splittedParam = param.split("=");
        validateLength(splittedParam);

        this.key = splittedParam[KEY_INDEX];
        this.value = splittedParam[VALUE_INDEX];
    }

    private void validateLength(String[] splittedParam) {
        if (splittedParam.length <= MIN_LENGTH) {
            throw new IllegalArgumentException(String.format("%s : 올바르지 않은 입력입니다.", splittedParam));
        }
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
