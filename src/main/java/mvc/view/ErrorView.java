package mvc.view;

import server.http.response.HttpResponse;
import was.http.HttpStatus;

public class ErrorView implements View {
    private final HttpStatus errorCode;

    public ErrorView(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public HttpResponse createResponse() {
        HttpResponse response = new HttpResponse();
        response.setStatus(errorCode);
        return response;
    }
}
