package exception;

import webserver.response.ModelAndView;
import webserver.response.ServletResponse;
import webserver.response.StatusCode;

public class InvalidRequestBodyException extends HttpRequestException {

    public InvalidRequestBodyException() {
        super("지원하지 않는 request body 형식입니다.");
    }

    @Override
    public ServletResponse getHandledResponse() {
        return ServletResponse.of(StatusCode.BAD_REQUEST,
            ModelAndView.of("static/notFound.html"));
    }
}
