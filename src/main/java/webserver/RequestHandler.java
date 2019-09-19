package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final UserController userController = UserController.getInstance();
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(br);

            sendResponse(out, getHttpResponse(httpRequest));

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse getHttpResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        List<String> resources = Arrays.asList("/index.html", "/user/create", "/user/form.html");
        String path = httpRequest.getPath();
        if (resources.contains(path)) {
            String result = "";
            if ("/user/create".equals(path)) {
                result = userController.create(httpRequest.getRequestParameter());
            }

            if (result.startsWith("redirect: ")) {
                String location = result.substring(result.indexOf(" ") + 1);
                HttpResponse httpResponse = HttpResponse.found(location);
                return httpResponse;
            }

            return create200Response("./templates/" + path);
        }
        return create200Response("./static/" + path);
    }

    private HttpResponse create200Response(String path) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        String[] extension = path.split("[.]");
        return HttpResponse.ok(body, extension[extension.length - 1]);
    }

    private void sendResponse(OutputStream out, HttpResponse httpResponse) {
        DataOutputStream dos = new DataOutputStream(out);

        try {
            byte[] response = httpResponse.serialize();
            dos.write(response, 0, response.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
