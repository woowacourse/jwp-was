package webserver.response;

import exception.NotInitializedResponseMetaDataException;
import view.ModelAndView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static webserver.response.ResponseMetaData.HEADER_NEW_LINE;

public class HttpResponse {

    private final DataOutputStream dos;
    private ResponseMetaData responseMetaData;

    public HttpResponse(final OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void makeResponse() throws IOException {
        writeResponseHeader();

        writeResponseBody(responseMetaData.getBody());
    }

    public void makeResponse(ModelAndView modelAndView) throws IOException {
        writeResponseHeader();

        writeResponseBody(modelAndView.buildView());
    }

    private void writeResponseHeader() throws IOException {
        if (responseMetaData == null) {
            throw new NotInitializedResponseMetaDataException();
        }

        dos.writeBytes(responseMetaData.getResponseLine() + HEADER_NEW_LINE);
        dos.writeBytes(responseMetaData.getHttpResponseHeaderFields());
    }

    private void writeResponseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void setResponseMetaData(ResponseMetaData responseMetaData) {
        this.responseMetaData = responseMetaData;
    }
}
