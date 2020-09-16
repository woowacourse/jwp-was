package webserver.http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class HttpResponse {
    private ResponseStatusLine responseStatus;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public HttpResponse(ResponseStatusLine responseStatusLine, ResponseHeaders responseHeaders) {
        this(responseStatusLine, responseHeaders, null);
    }

    public HttpResponse(ResponseStatusLine responseStatus, ResponseHeaders responseHeaders,
        ResponseBody responseBody) {
        this.responseStatus = responseStatus;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public void write(DataOutputStream dos) throws IOException {
        responseStatus.write(dos);
        responseHeaders.write(dos);
        if (Objects.nonNull(responseBody)) {
            responseBody.write(dos);
        }
        dos.flush();
    }
}
