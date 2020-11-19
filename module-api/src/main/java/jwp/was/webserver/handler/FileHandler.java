package jwp.was.webserver.handler;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static jwp.was.util.HttpMethod.GET;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import jwp.was.dto.HttpRequest;
import jwp.was.dto.HttpResponse;
import jwp.was.util.HttpStatusCode;
import jwp.was.webserver.ExtensionMimeMatcher;
import jwp.was.webserver.utils.FileIoUtils;
import jwp.was.webserver.utils.FileNotExitsException;
import jwp.was.webserver.utils.ResponseUtils;

public class FileHandler {

    FileHandler() {
    }

    protected void loadFile(OutputStream out, HttpRequest httpRequest) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            loadFile(httpRequest, dos);
        }
    }

    private void loadFile(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        boolean isNotGetHttpMethod = !GET.isSame(httpRequest.getHttpMethod());
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
            = HttpResponse.of(httpRequest.getHttpVersion(), HttpStatusCode.METHOD_NOT_ALLOW, body);
        ResponseUtils.response(dos, httpResponse);
    }

    private void returnOk(HttpRequest httpRequest, DataOutputStream dos)
        throws IOException, URISyntaxException {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, ExtensionMimeMatcher.getMimeType(httpRequest.getUrlPath()));

        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getUrlPath());

        HttpResponse httpResponse
            = HttpResponse.of(httpRequest.getHttpVersion(), HttpStatusCode.OK, headers, body);
        ResponseUtils.response(dos, httpResponse);
    }

    private void returnNotFound(HttpRequest httpRequest, DataOutputStream dos,
        Exception e) throws IOException {
        HttpResponse httpResponse = HttpResponse
            .of(httpRequest.getHttpVersion(), HttpStatusCode.NOT_FOUND, e.getMessage());
        ResponseUtils.response(dos, httpResponse);
    }
}
