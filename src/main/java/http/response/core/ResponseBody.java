package http.response.core;

import http.request.HttpRequest;
import http.request.core.RequestVersion;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResponseBody {
    private final HttpRequest httpRequest;
    private final ResponseStatus responseStatus;
    private final String headerLine;
    private final StringBuilder body;

    public ResponseBody(HttpRequest httpRequest, ResponseStatus responseStatus, String headerLine) {
        this.httpRequest = httpRequest;
        this.responseStatus = responseStatus;
        this.headerLine = headerLine;
        body = new StringBuilder();
    }

    public List<Object> getBody() throws IOException, URISyntaxException {
        RequestVersion version = httpRequest.getRequestVersion();
        body.append(version.getVersion()).append(" ");
        body.append(responseStatus.getResponseStatusHeader());
        body.append(headerLine);
        if(responseStatus.getHttpStatusCode() == 302) {
            return Collections.singletonList(body);
        }
        byte[] byteBodyData = getByteBody();
        body.append("Content-Length: ").append(byteBodyData.length).append("\r\n").append("\r\n");
        return Arrays.asList(body, byteBodyData);
    }

    private byte[] getByteBody() throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(httpRequest.getRequestPath().getFullPath());
    }
}
