package webserver;

import controller.Controller;
import controller.CreateUserController;
import model.Request;
import model.RequestParser;
import model.Response;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
    }

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            RequestParser requestParser = new RequestParser(in);
            Request request = new Request(requestParser.getHeaderInfo(), requestParser.getParameter());

            String url = request.getUrl();
            String extension = url.substring(url.lastIndexOf(".") + 1);

            a(dos, request, url, extension);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void a(DataOutputStream dos, Request request, String url, String extension) throws URISyntaxException, IOException {
        if (!extension.startsWith("/")) {
            if ("html".equals(extension)) {
                String classPath = "./templates" + url;
                processResponse(dos, classPath);
                return;
            }
            String classPath = "./static" + url;
            processResponse(dos, classPath);
        }

        if (extension.startsWith("/")) {
            Response response = new Response(dos);

            Controller controller = controllers.get(request.getUrl());
            controller.service(request, response);
        }
    }

    private void processResponse(DataOutputStream dos, String classPath) throws URISyntaxException, IOException {
        Response response = new Response(dos);

        Path path = Paths.get(Objects.requireNonNull(FileIoUtils.class.getClassLoader().getResource(classPath)).toURI());
        File file = new File(path.toString());
        String mimeType = new Tika().detect(file);

        byte[] body = FileIoUtils.loadFileFromClasspath(classPath);

        response.setHeader("Status", "HTTP/1.1 200 OK \r\n");
        response.setHeader("Content-Type", mimeType + ";charset=utf-8\r\n");
        response.setHeader("Content-Length", body.length + "\r\n");

        response.response200(body);
    }
}

