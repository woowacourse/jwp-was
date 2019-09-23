package http.response.view;

import http.response.ResponseStatus;

public class ErrorView extends View {

    public ErrorView(ResponseStatus responseStatus, String message) {
        super(responseStatus);
        this.body = message.getBytes();
    }

}
