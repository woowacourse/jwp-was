package controller;

import http.HttpStatusCode;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import http.response.HttpResponseHeader;
import http.response.HttpResponseStatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String uri = httpRequest.getUri();

        HttpResponseStatusLine httpResponseStatusLine;
        HttpResponseHeader HttpResponseHeader;
        HttpResponseBody httpResponseBody;
        try {
            httpResponseStatusLine = new HttpResponseStatusLine(httpRequest.getHttpVersion(), HttpStatusCode.of("200"));
            HttpResponseHeader = new HttpResponseHeader("Location: /" + httpRequest.getUri());
            httpResponseBody = new HttpResponseBody(uri);
            httpResponse.send200Ok(uri);
        } catch (IOException | URISyntaxException e) {
//            httpResponseStatusLine = new HttpResponseStatusLine(httpRequest.getHttpVersion(), HttpStatusCode.of("404"));
//            HttpResponseHeader = new HttpResponseHeader("");
//            httpResponseBody = new HttpResponseBody("");
            logger.error(e.getMessage());
        }

//        httpResponse.setHttpResponseStatusLine(httpResponseStatusLine);
//        httpResponse.setHttpResponseHeader(HttpResponseHeader);
//        httpResponse.setHttpResponseBody(httpResponseBody);
    }
}
