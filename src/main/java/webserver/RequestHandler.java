package webserver;

import controller.Controller;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import model.general.ContentType;
import model.general.Header;
import model.request.Request;
import model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger
            .debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = connection.getOutputStream()) {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            Request request = Request.of(inputStream);

            ContentType responseContentType = request.generateContentTypeFromRequestUri();
            Controller controller = Controller.of(responseContentType);
            Response response = controller.executeOperation(request);

            writeToOutputStream(dataOutputStream, response);
        } catch (IOException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void writeToOutputStream(DataOutputStream dataOutputStream, Response response)
        throws IOException {
        writeStatusLine(dataOutputStream, response);
        writeHeaders(dataOutputStream, response);
        writeBody(dataOutputStream, response);
    }

    private void writeStatusLine(DataOutputStream dataOutputStream, Response response)
        throws IOException {
        dataOutputStream.writeBytes(
            response.getHttpVersion()
                + " "
                + response.getStatusCode()
                + " "
                + response.getReasonPhrase()
                + " \r\n");
    }

    private void writeHeaders(DataOutputStream dataOutputStream, Response response)
        throws IOException {
        Map<Header, String> headers = response.getHeaders();

        for (Map.Entry<Header, String> entry : headers.entrySet()) {
            dataOutputStream
                .writeBytes(entry.getKey().getName() + ": " + entry.getValue() + "\r\n");
        }
        dataOutputStream.writeBytes("\r\n");
    }

    private void writeBody(DataOutputStream dataOutputStream, Response response)
        throws IOException {
        if (response.hasContents()) {
            byte[] body = response.getBody();
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        }
    }
}
