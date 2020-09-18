package webserver;

import http.*;
import http.path.Index;
import http.path.Path;
import http.path.RawFile;
import http.path.UserCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            router(br, dos); //TODO router 보다는 route가 명시적이다
        } catch (IOException | NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    private void router(BufferedReader br, DataOutputStream dos) throws IOException {
        RequestLine requestLine = new RequestLine(br);
        RequestHeader requestHeader = new RequestHeader(br);
        Map<String, Path> paths = new HashMap<>();
        paths.put("/user/create", new UserCreate());
        paths.put("/", new Index());

        Path path = paths.getOrDefault(requestLine.getPath(), new RawFile(requestLine.getPath()));

        if (requestLine.isMethodEqualTo("GET")) {
            path.get(dos, requestHeader);
        } else if (requestLine.isMethodEqualTo("POST")) {
            RequestBody requestBody = new RequestBody(br, requestHeader.getContentLength());
            path.post(dos, requestHeader, requestBody);
        }
    }
}
