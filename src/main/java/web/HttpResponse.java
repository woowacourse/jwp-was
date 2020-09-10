package web;

import static web.HttpRequest.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import utils.FileIoUtils;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTML_PATH_PREFIX = "./templates";
    private static final String HTML_FILE = ".html";
    public static final String INDEX_HTML = "/index.html";
    public static final String ROOT_PATH = "/";
    public static final int PARAMETER_INDEX = 1;

    private DataOutputStream dos;
    private List<String> response;
    private byte[] content;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        this.response = new ArrayList<>();
    }

    public void process(HttpRequest request) {
        String path = request.getPath();
        if (ROOT_PATH.equals(path)) {
            processHtml(INDEX_HTML);
        } else if (path.contains(HTML_FILE)) {
            processHtml(path);
        } else {
            processApi(path);
        }
    }

    private void processHtml(String path) {
        try {
            this.content = FileIoUtils.loadFileFromClasspath(
                    HTML_PATH_PREFIX + path);
            response200Header();
            responseBody();
            logger.debug(toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void processApi(String path) {
        Map<String, String> params = parsingParameters(path.split("\\?")[PARAMETER_INDEX]);
        User user = new User(params.get("userId"), params.get("password"), params.get("name"),
                params.get("email"));
    }

    private Map<String, String> parsingParameters(String parameters) {
        String[] parametersArr = parameters.split("&");
        return Arrays.stream(parametersArr)
                .collect(Collectors.toMap(param -> param.split("=")[0],
                        param -> param.split("=")[1]));
    }

    private void response200Header() {
        response.add("HTTP/1.1 200 OK");
        response.add("Content-Type: text/html;charset=utf-8");
        response.add("Content-Length: " + content.length);

        response.forEach(value -> {
            try {
                dos.writeBytes(value + NEW_LINE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            dos.writeBytes(NEW_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void responseBody() {
        try {
            dos.write(content, 0, content.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "header=" + response +
                '}';
    }
}
