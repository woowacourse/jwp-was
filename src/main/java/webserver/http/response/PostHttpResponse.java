package webserver.http.response;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class PostHttpResponse extends HttpResponse {
    private static final String REDIRECT_URL = "/index.html";

    @Override
    public void handleResponse(DataOutputStream dos) throws IOException, URISyntaxException {
        String body = httpRequests.getBody();
        String decodedBody = URLDecoder.decode(body, "UTF-8");
        parseUserParameters(decodedBody);
        byte[] redirectBody = FileIoUtils.loadFileFromClasspath(REDIRECT_URL);
        response302Header(dos, redirectBody.length);
        responseBody(dos, redirectBody);
    }

    private void parseUserParameters(String decodedBody) {
        Map<String, String> parameters = new HashMap<>();
        String[] urlParameters = decodedBody.split(URL_PARAMETER_REGEX);
        for (String parameter : urlParameters) {
            String[] entry = parameter.split(PARAMETER_REGEX);
            parameters.put(entry[0], entry[1]);
        }
        createUser(parameters);
    }

    private void response302Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("Location: " + "http://localhost:8080/index.html");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
