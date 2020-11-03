package exception;

import webserver.response.ModelAndView;
import webserver.response.ServletResponse;
import webserver.response.StatusCode;

public class MethodNotAllowedException extends HttpRequestException {

    public MethodNotAllowedException() {
        super("올바른 핸들러 메서드를 발견하지 못했습니다.");
    }

    @Override
    public ServletResponse getHandledResponse() {
        return ServletResponse.of(StatusCode.METHOD_NOT_ALLOWED,
            ModelAndView.of("static/notFound.html"));
    }
}
