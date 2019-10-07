package http.parameter;

public class ParameterNotFoundException extends RuntimeException {
    public ParameterNotFoundException() {
        super();
    }

    private ParameterNotFoundException(String parameterKey) {
        super(String.format("[%s] 을 키로 사용하는 파라미터가 존재하지 않습니다.", parameterKey));
    }

    public static ParameterNotFoundException ofParameterKey(String parameterKey) {
        return new ParameterNotFoundException(parameterKey);
    }
}
