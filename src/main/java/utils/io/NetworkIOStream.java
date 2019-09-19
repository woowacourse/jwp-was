package utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NetworkIOStream implements NetworkIO {
    private static final Logger logger = LoggerFactory.getLogger(NetworkIOStream.class);

    private final InputStream in;
    private final OutputStream out;
    private final BufferedReader reader;
    private final DataOutputStream writer;

    public NetworkIOStream(Socket connection) throws IOException {
        this.in = connection.getInputStream();
        this.out = connection.getOutputStream();
        this.reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.writer = new DataOutputStream(out);
    }

    @Override
    public boolean hasNext() {
        try {
            return this.reader.ready();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }

    }

    @Override
    public String readLine() {
        try {
            return this.reader.readLine();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void write(byte[] body) {
        try {
            this.writer.write(body);
            this.writer.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        this.in.close();
        this.out.close();
    }
}