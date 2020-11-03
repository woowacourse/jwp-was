package exception;

import webserver.response.ModelAndView;
import webserver.response.ServletResponse;
import webserver.response.StatusCode;

public class MethodParameterBindException extends HttpRequestException {

    public MethodParameterBindException() {
        super("파라미터 매핑 예외");
    }

    @Override
    public ServletResponse getHandledResponse() {
        return ServletResponse.of(StatusCode.BAD_REQUEST,
            ModelAndView.of("static/notFound.html"));
    }
}
