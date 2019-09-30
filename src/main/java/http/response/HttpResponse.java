package http.response;

import http.HttpSessionStore;
import http.HttpStatusCode;
import http.MediaType;
import http.request.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpResponseStatusLine httpResponseStatusLine;
    private HttpResponseHeader httpResponseHeader;
    private HttpResponseBody httpResponseBody;

    public static HttpResponse of(HttpRequest httpRequest) {
        HttpResponseStatusLine httpResponseStatusLine = new HttpResponseStatusLine(httpRequest.getHttpVersion());

        String sessionId = httpRequest.hasSession() ? httpRequest.getSessionId() : HttpSessionStore.getSession("").getId();

        HttpResponseHeader httpResponseHeader = new HttpResponseHeader();
        httpResponseHeader.addCookie("SessionId=" + sessionId);

        return new HttpResponse(httpResponseStatusLine, httpResponseHeader);
    }

    public HttpResponse(HttpResponseStatusLine httpResponseStatusLine, HttpResponseHeader httpResponseHeader) {
        this.httpResponseStatusLine = httpResponseStatusLine;
        this.httpResponseHeader = httpResponseHeader;
    }

    public void setCookie(String cookieValue) {
        httpResponseHeader.addField("Set-cookie", cookieValue);
    }

    public void setStatusCode(HttpStatusCode httpStatusCode) {
        httpResponseStatusLine.setHttpStatusCode(httpStatusCode);
    }

    public void redirect(String uri) {
        String contentType = MediaType.getContentType(uri);
        httpResponseHeader.addField("Content-Type", contentType + ";charset=utf-8");
        httpResponseHeader.setLocation(uri);
    }

    public void forward(String uri) throws IOException, URISyntaxException {
        String contentType = MediaType.getContentType(uri);

        logger.debug("request uri : {}", uri);
        logger.debug("request file full path : {}", MediaType.getFullPath(uri));
        logger.debug("request file contentType : {}", MediaType.getContentType(uri));

        this.httpResponseBody = new HttpResponseBody(FileIoUtils.loadFileFromClasspath(MediaType.getFullPath(uri)));
        httpResponseHeader.addField("Content-Type", contentType + ";charset=utf-8");
        httpResponseHeader.addField("Content-Length", String.valueOf(httpResponseBody.getBodyLength()));
    }

    public void writeResponse(DataOutputStream dataOutputStream) throws IOException {
        logger.debug("response status line");
        logger.debug("response header");


        dataOutputStream.writeBytes(httpResponseStatusLine.toString());
        dataOutputStream.writeBytes(httpResponseHeader.toString());

        logger.debug("response status line: {}", httpResponseStatusLine.toString());
        logger.debug("response header: {}", httpResponseHeader.toString());

        if (httpResponseBody != null) {
            logger.debug("response header: {}", this.httpResponseBody.getBody());

            dataOutputStream.write(this.httpResponseBody.getBody(), 0, this.httpResponseBody.getBodyLength());
        }

        dataOutputStream.flush();
    }
}
