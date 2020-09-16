package webserver.http.response;

import db.DataBase;
import model.User;
import model.UserFactory;
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
    private static final String URL_PARAMETER_REGEX = "&";
    private static final String PARAMETER_REGEX = "=";

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
        responseBody(dos, body);
    }

    private Map<String, String> parseUserParameters(String url) throws UnsupportedEncodingException {
        String decodedUrl = URLDecoder.decode(url, "UTF-8");
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

    private void createUser(Map<String, String> parameters) {
        User user = UserFactory.create(parameters);
        DataBase.addUser(user);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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
