package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class NetworkIOStream implements NetworkIO {
    private static final Logger logger = LoggerFactory.getLogger(NetworkIOStream.class);

    private final InputStream in;
    private final OutputStream out;
    private final BufferedReader reader;
    private final DataOutputStream writer;

    public static Optional<NetworkIOStream> init(Socket connection) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = connection.getInputStream();
            out = connection.getOutputStream();
            return Optional.of(new NetworkIOStream(in, out));
        } catch (IOException e) {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ee) {
                logger.error(ee.getMessage());
            }
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    private NetworkIOStream(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
        this.reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.writer = new DataOutputStream(out);
    }

    @Override
    public boolean hasNext() {
        try {
            return reader.ready();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<String> readLine() {
        try {
            return Optional.of(reader.readLine());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void write(String body) {
        try {
            writer.writeBytes(body);
            writer.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            if (this.in != null) {
                this.in.close();
            }
            if (this.out != null) {
                out.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public DataOutputStream getDos() {
        return this.writer;
    }
}