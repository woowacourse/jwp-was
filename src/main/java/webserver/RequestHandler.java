package webserver;

import static utils.IOUtils.writeWithLineSeparator;

import db.DataBase;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = requestParser(in);

            DataOutputStream dos = new DataOutputStream(out);
            postRequestHandle(request, dos);
            response(request, dos);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response(Request request, DataOutputStream dos) throws IOException, URISyntaxException {
        if (request.isGet()) {
            byte[] fileData = fileDataFinder(request);
            response200Header(request, dos, fileData.length);
            responseBody(dos, fileData);
        }
    }

    private Request requestParser(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line;
        List<String> lines = new ArrayList<>();

        while (!"".equals(line = br.readLine()) && line != null) {
            logger.debug(line);
            lines.add(line);
        }

        return new Request(lines, br);
    }

    private void response200Header(Request request, DataOutputStream dos, int lengthOfBodyContent) {
        AcceptType type = request.getType();
        try {
            writeWithLineSeparator(dos, "HTTP/1.1 200 OK ");
            writeWithLineSeparator(dos, "Content-Type: " + type.getContentType() + ";charset=utf-8");
            writeWithLineSeparator(dos, "Content-Length: " + lengthOfBodyContent);
            dos.writeBytes(System.lineSeparator());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            writeWithLineSeparator(dos, "HTTP/1.1 302 Found ");
            writeWithLineSeparator(dos, "Location: http://localhost:8080/index.html ");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void postRequestHandle(Request request, DataOutputStream dos) {
        if (request.isPost()) {
            User user = request.getBody(User.class);

            DataBase.addUser(user);
            response302Header(dos);
        }
    }

    private byte[] fileDataFinder(Request request) throws IOException, URISyntaxException {
        AcceptType type = request.getType();
        return FileIoUtils.loadFileFromClasspath(type.getFileRootPath() + request.getHeader("filePath"));
    }
}
