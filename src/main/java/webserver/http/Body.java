package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Body {
    private static final Logger LOGGER = LoggerFactory.getLogger(Body.class);

    private final BodyState bodyState;
    private final String content;

    private Body(BodyState bodyState, String content) {
        this.bodyState = bodyState;
        this.content = content;
    }

    public static Body of(BufferedReader bufferedReader, String contentLength) throws IOException {
        String body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));
        return new Body(BodyState.NOT_EMPTY, body);
    }

    private static boolean isNullLine(String line) {
        return Objects.isNull(line);
    }

    public static Body of(byte[] body) {
        String content = new String(body, StandardCharsets.UTF_8);
        if (isNullLine(content)) {
            LOGGER.info("empty body create clear!");
            return new Body(BodyState.EMPTY, null);
        }
        LOGGER.info("not empty body create clear!");
        return new Body(BodyState.NOT_EMPTY, content);
    }

    public static Body emptyBody() {
        return new Body(BodyState.EMPTY, null);
    }

    public Parameters getParameters() {
        return Parameters.notEmptyQueryParameters(content);
    }

    public byte[] getContent() {
        return content.getBytes();
    }

    public int getLength() {
        return content.getBytes().length;
    }

    public BodyState getState() {
        return bodyState;
    }
}
