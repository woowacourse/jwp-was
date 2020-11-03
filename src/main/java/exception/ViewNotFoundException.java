package exception;

import webserver.response.ModelAndView;
import webserver.response.ServletResponse;
import webserver.response.StatusCode;

public class ViewNotFoundException extends HttpRequestException {

    public ViewNotFoundException(final String viewName) {
        super(viewName + "을 찾을 수 없습니다.");
    }

    public ServletResponse getHandledResponse() {
        return ServletResponse.of(StatusCode.NOT_FOUND, ModelAndView.of("static/notFound.html"));
    }
}
