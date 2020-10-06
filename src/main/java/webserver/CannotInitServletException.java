package webserver;

public class CannotInitServletException extends ServerException {
    private static final String ERROR_MESSAGE = "해당 요청에 대한 서블릿이 존재하지 않습니다.";

    public CannotInitServletException(String requestPath) {
        super(String.format("%s : {'Request Path' : %s}",ERROR_MESSAGE, requestPath));
    }
}
