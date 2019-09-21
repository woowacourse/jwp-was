package webserver;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import http.response.HttpResponseFactory;
import http.response.response_entity.Http200ResponseEntity;
import http.response.response_entity.HttpResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final String STYLESHEET = "css";
    private static final String DEFAULT_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequestFactory.makeHttpRequest(in);
            logger.debug(request.toString());

            HttpResponse response = getResponse(request);
            writeResponse(out, response);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse getResponse(HttpRequest request) throws IOException, URISyntaxException {
        HttpResponseEntity responseEntity = request.getUri().hasExtension()
                ? new Http200ResponseEntity(request.getUri().getPath())
                : ControllerMapper.map(request);

        String viewTemplatePath = responseEntity.getViewTemplatePath();
        HttpResponse httpResponse = HttpResponseFactory.makeResponse(responseEntity);

        setBody(viewTemplatePath, httpResponse);
        return httpResponse;
    }

    private void setBody(String viewTemplatePath, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (viewTemplatePath != null) {
            httpResponse.setBody(getBody(viewTemplatePath), getMediaType(viewTemplatePath));
        }
    }

    private byte[] getBody(String path) throws IOException, URISyntaxException {
        byte[] body;
        try {
            body = FileIoUtils.loadFileFromClasspath(DEFAULT_PATH + path);
        } catch (NullPointerException e) {
            body = FileIoUtils.loadFileFromClasspath(STATIC_PATH + path);
        }
        return body;
    }

    private String getMediaType(String path) {
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        String[] splicedFileName = fileName.split("\\.");
        String extension = splicedFileName[splicedFileName.length - 1];
        return extension.equals(STYLESHEET)
                ? "text/css"
                : "text/html";
    }

    private void writeResponse(OutputStream out, HttpResponse response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(response.getHeaderMessage());
        if (response.hasBody()) {
            dos.write(response.getBody());
        }
    }
}
