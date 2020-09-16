package webserver.http.response;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class GetHttpResponse extends HttpResponse {
    private static final String QUESTION_MARK = "?";
    private static final String URL_REGEX = "\\?";
    private static final String CONTENT_TYPE_CSS = "text/css";
    private static final String CONTENT_TYPE_HTML = "text/html";

    @Override
    public void handleResponse(DataOutputStream dos) throws IOException, URISyntaxException {
        String url = httpRequests.getUrl();
        if (url.contains(QUESTION_MARK)) {
            Map<String, String> parameters = parseUserParameters(url);
            createUser(parameters);
            return;
        }
        byte[] body = FileIoUtils.loadFileFromClasspath(url);
        response200Header(dos, body.length);
        responseContentType(dos, url);
        responseBody(dos, body);
    }

    private Map<String, String> parseUserParameters(String url) throws UnsupportedEncodingException {
        String decodedUrl = URLDecoder.decode(url, ENCODING_TYPE);
        Map<String, String> parameters = new HashMap<>();
        String[] urls = decodedUrl.split(URL_REGEX);
        String urlParameter = urls[1];
        String[] urlParameters = urlParameter.split(URL_PARAMETER_REGEX);
        for (String parameter : urlParameters) {
            String[] entry = parameter.split(PARAMETER_REGEX);
            parameters.put(entry[0], entry[1]);
        }
        return parameters;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void responseContentType(DataOutputStream dos, String url) {
        try {
            String contentType = decideContentTypeByUrl(url);
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private String decideContentTypeByUrl(String url) {
        if (url.endsWith(".css")) {
            return CONTENT_TYPE_CSS;
        }
        return CONTENT_TYPE_HTML;
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
