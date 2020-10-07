package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.message.HttpResponseMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ResponseWriter implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(ResponseWriter.class);

    private final DataOutputStream dos;

    public ResponseWriter(Socket connection) throws IOException {
        this.dos = new DataOutputStream(connection.getOutputStream());
    }

    public void write(HttpResponseMessage httpResponseMessage) {
        byte[] message = httpResponseMessage.toHttpMessage().getBytes();

        try {
            dos.write(message, 0, message.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        this.dos.close();
    }
}
