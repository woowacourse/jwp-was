package http.response;

import java.io.DataOutputStream;

public class HttpResponse {
    private final DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }
}
