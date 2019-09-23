package webserver.response;

import exception.IllegalFilePathException;
import utils.FileIoUtils;
import webserver.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;

public class OkResponseMetaData extends AbstractResponseMetaData {

    private static final String DEFAULT_CHARSET = "utf-8";
    private static final HttpStatus httpStatus = HttpStatus.OK;

    public OkResponseMetaData(HttpRequest httpRequest) {
        super(httpRequest);
        responseHeaderFields.put("Content-Type", httpRequest.findContentType() + ";charset=" + DEFAULT_CHARSET);
        responseHeaderFields.put("Content-Length", String.valueOf(getBody().length));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public boolean hasBody() {
        return true;
    }

    @Override
    public byte[] getBody() {
        try {
            return FileIoUtils.loadFileFromClasspath(httpRequest.findFilePath());
        } catch (IOException | URISyntaxException e) {
            throw new IllegalFilePathException(e);
        }
    }
}
