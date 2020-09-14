package webserver.handler;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static webserver.HttpMethod.GET;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import webserver.FileNameExtension;
import webserver.HttpStatusCode;
import webserver.dto.HttpRequest;
import webserver.dto.HttpResponse;
import webserver.utils.FileIoUtils;
import webserver.utils.FileNotExitsException;
import webserver.utils.ResponseUtils;

public class FileHandler {

    private static final String DEFAULT_CONTENT_TYPE = "text/plane";
    private static final String CSS_CONTENT_TYPE = "text/css";

    FileHandler() {
    }

    protected void loadFile(OutputStream out, HttpRequest httpRequest) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            loadFile(httpRequest, dos);
        }
    }

    private void loadFile(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        boolean isNotGetHttpMethod = !GET.equalsHttpMethodIgnoreCase(httpRequest.getHttpMethod());
        if (isNotGetHttpMethod) {
            returnMethodNotAllow(httpRequest, dos);
            return;
        }
        try {
            returnOk(httpRequest, dos);
        } catch (URISyntaxException | FileNotExitsException e) {
            returnNotFound(httpRequest, dos, e);
        }
    }

    private void returnMethodNotAllow(HttpRequest httpRequest, DataOutputStream dos)
        throws IOException {
        String body = "지원하지 않는 메서드를 사용하셨습니다.";
        HttpResponse httpResponse
            = HttpResponse.of(httpRequest.getProtocol(), HttpStatusCode.METHOD_NOT_ALLOW, body);
        ResponseUtils.response(dos, httpResponse);
    }

    private void returnOk(HttpRequest httpRequest, DataOutputStream dos)
        throws IOException, URISyntaxException {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, getContentType(httpRequest));

        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getUrlPath(),
            httpRequest.getDirectory());

        HttpResponse httpResponse
            = HttpResponse.of(httpRequest.getProtocol(), HttpStatusCode.OK, headers, body);
        ResponseUtils.response(dos, httpResponse);
    }

    private String getContentType(HttpRequest httpRequest) throws IOException {
        String contentType = Files.probeContentType(Paths.get(httpRequest.getUrlPath()));
        if (Objects.nonNull(contentType)) {
            return contentType;
        }
        if (httpRequest.getFileNameExtension().equals(FileNameExtension.CSS)) {
            return CSS_CONTENT_TYPE;
        }
        return DEFAULT_CONTENT_TYPE;
    }

    private void returnNotFound(HttpRequest httpRequest, DataOutputStream dos,
        Exception e) throws IOException {
        HttpResponse httpResponse
            = HttpResponse.of(httpRequest.getProtocol(), HttpStatusCode.NOT_FOUND, e.getMessage());
        ResponseUtils.response(dos, httpResponse);
    }
}
