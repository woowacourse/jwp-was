package utils.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.recursion.Done;
import utils.recursion.TailCall;
import utils.recursion.TailRecursion;

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
        try {
            return Optional.of(new NetworkIOStream(connection));
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    private NetworkIOStream(Socket connection) throws IOException, InterruptedException {
        this.in = connection.getInputStream();
        this.out = connection.getOutputStream();
        this.reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.writer = new DataOutputStream(out);
        for (int i = 0; !this.reader.ready() && i < 32768; i++) {
            Thread.sleep(1);
        }
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
        return readLine(new StringBuilder()).get();
    }

    private TailRecursion<String> readLine(StringBuilder acc) {
        try {
            return this.reader.ready() ? checkIfLineBreak(acc) : (Done<String>) acc::toString;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return (Done<String>) acc::toString;
        }
    }

    private TailRecursion<String> checkIfLineBreak(StringBuilder acc) throws IOException {
        final int c = this.reader.read();
        if (c == '\r') {
            this.reader.mark(1);
            if (this.reader.ready() && this.reader.read() == '\n') {
                return (Done<String>) () -> acc.append("\r\n").toString();
            }
            this.reader.reset();
            return (Done<String>) () -> acc.append("\r").toString();
        }
        if (c == '\n') {
            return (Done<String>) () -> acc.append("\n").toString();
        }
        return (TailCall<String>) () -> readLine(acc.append((char) c));
    }

    @Override
    public String readAllLeft() {
        return readAllLeft(new StringBuilder()).get();
    }

    private TailRecursion<String> readAllLeft(StringBuilder acc) {
        try {
            return this.reader.ready() ? continueReading(acc) : (Done<String>) acc::toString;
        } catch (IOException e) {
            return (Done<String>) acc::toString;
        }
    }

    private TailRecursion<String> continueReading(StringBuilder acc) throws IOException {
        return readAllLeft(acc.append((char) this.reader.read()));
    }

    @Override
    public void write(String body) {
        try {
            this.writer.write(body.getBytes());
            this.writer.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            this.in.close();
            this.out.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}