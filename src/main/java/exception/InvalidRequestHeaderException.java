package exception;

import webserver.response.ModelAndView;
import webserver.response.ServletResponse;
import webserver.response.StatusCode;

public class InvalidRequestHeaderException extends HttpRequestException {

    public InvalidRequestHeaderException() {
        super("지원하지 않는 request header 형식입니다.");
    }

    @Override
    public StatusCode getStatusCode() {
        return StatusCode.BAD_REQUEST;
    }
}
