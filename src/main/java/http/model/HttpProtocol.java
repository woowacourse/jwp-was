package http.model;

import http.supoort.IllegalHttpRequestException;

public class HttpProtocol {
    private String protocol;

    public HttpProtocol(String protocol) {
        validate(protocol);
        this.protocol = protocol;
    }

    private void validate(String protocol) {
        if(!protocol.startsWith("HTTP/")){
            throw new IllegalHttpRequestException();
        }
    }
}
