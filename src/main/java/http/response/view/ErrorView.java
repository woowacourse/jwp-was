package http.response.view;

import http.response.ResponseStatus;

public class ErrorView extends View {
    private final ResponseStatus responseStatus;

    public ErrorView(int code, String message) {
        this.responseStatus = ResponseStatus.byCode(code);
        this.body = message.getBytes();
    }

    @Override
    public String getHeader() {
        return super.getHeader(responseStatus);
    }

}
