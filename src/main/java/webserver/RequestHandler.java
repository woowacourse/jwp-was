package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DataConverter;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.message.request.Request;
import webserver.message.response.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final Request request = new Request(new IOUtils(in));
            final byte[] response = processRequest(request);
            final DataOutputStream dos = new DataOutputStream(out);
            writeResponse(dos, response);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.error("run() {}", e.getMessage());
        }
    }

    // RequestHandler의 makeFilePath 메서드에서는 인덱스일 때만 처리하고
    // FileLoader의 makeFilePath 메서드에서는 다른 모든 파일들을 처리하니까
    // 얘네를 합치자
    private String makeFilePath(final Request requestHeader, final String prefix) {
        final String requestPath = requestHeader.getPath();
        final String pathEnd = (requestPath.endsWith("/") || "".equals(requestPath)) ? "index.html" : "";
        return prefix + requestPath + pathEnd;
    }

    private byte[] processRequest(final Request request) throws IOException, URISyntaxException, NullPointerException {
        try {
            final Response response = RequestDispatcher.forward(request);
            return Objects.nonNull(response) ? DataConverter.convertToBytes(response) :
                    DataConverter.convertToBytes(FileIoUtils.loadFileFromClasspath(makeFilePath(request, STATIC_PATH)));
        } catch (IOException | URISyntaxException | NullPointerException e) {
            return DataConverter.convertToBytes(FileIoUtils.loadFileFromClasspath(makeFilePath(request, TEMPLATES_PATH)));
        }
    }

    private void writeResponse(final DataOutputStream dos, final byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error("writeResponse() {}", e.getMessage());

        }
    }
}
