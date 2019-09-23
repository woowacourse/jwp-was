package webserver.response;

import exception.NotInitializedResponseMetaDataException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static webserver.response.AbstractResponseMetaData.HEADER_NEW_LINE;

public class HttpResponse {

    private final DataOutputStream dos;
    private ResponseMetaData responseMetaData;

    public HttpResponse(final OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void makeResponse() throws IOException {
        if (responseMetaData == null) {
            throw new NotInitializedResponseMetaDataException();
        }

        dos.writeBytes(responseMetaData.getResponseLine() + HEADER_NEW_LINE);
        dos.writeBytes(responseMetaData.getHttpResponseHeaderFields());

        if (responseMetaData.hasBody()) {
            responseBody(responseMetaData.getBody());
        }
    }

    private void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void setResponseMetaData(ResponseMetaData responseMetaData) {
        this.responseMetaData = responseMetaData;
    }
}
