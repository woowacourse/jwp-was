package webserver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import db.DataBase;
import http.request.HttpMethod;
import http.request.RequestBody;
import http.request.RequestEntity;
import model.User;
import utils.FileIoUtils;
import utils.StringUtils;

public class RequestMappingStorage {

    public static final String FILE_PATH =  "/static/files";

    private static List<RequestMapping> REQUEST_MAPPINGS;

    static {
        REQUEST_MAPPINGS = Arrays.asList(
            new RequestMapping(HttpMethod.GET, FILE_PATH, RequestMappingStorage::fileMapping),
            new RequestMapping(HttpMethod.POST, "/user/create", (RequestMappingStorage::userCreateMapping))
        );
    }

    public static RequestMapping findMatchingMapping(RequestEntity requestEntity) {
        return REQUEST_MAPPINGS.stream()
            .filter(requestMapping ->
                requestMapping.isMapping(requestEntity.getHttpMethod(), requestEntity.getHttpUrl().getPath()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("해당 mapping이 존재하지 않습니다."));
    }

    private static InputStream fileMapping(RequestEntity requestEntity) {
        String path = requestEntity.getHttpUrl().getPath();
        String localPath = parseToLocalPath(path);
        byte[] body = FileIoUtils.loadFileFromClasspath(localPath);
        String header =
            "HTTP/1.1 200 OK \r\n" +
                "Content-Type: " + parseContentType(path) + "\r\n" +
                "Content-Length: " + body.length + "\r\n" +
                "\r\n";

        byte[] response = ArrayUtils.addAll(header.getBytes(), body);
        return new ByteArrayInputStream(response);
    }

    private static InputStream userCreateMapping(RequestEntity requestEntity) {
        RequestBody requestBody = requestEntity.getRequestBody();
        Map<String, String> userInfo = StringUtils.extractParams(requestBody.getContent());
        User user = new User(
            userInfo.get("userId"), userInfo.get("password"), userInfo.get("name"), userInfo.get("email"));
        DataBase.addUser(user);

        String response = "HTTP/1.1 302 FOUND \r\n" +
            "Location: /index.html \r\n" +
            "\r\n";
        return new ByteArrayInputStream(response.getBytes());
    }

    private static String parseToLocalPath(String path) {
        if (path.endsWith(".html") || path.endsWith(".ico")) {
            return "./templates" + path;
        }
        return "./static" + path;
    }

    private static String parseContentType(String path) {
        if (path.endsWith(".css")) {
            return "text/css";
        }
        return "text/html;charset=utf-8";
    }
}
