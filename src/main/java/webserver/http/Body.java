package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class Body {
    private final BodyState bodyState;
    private final String content;

    private Body(BodyState bodyState, String content) {
        this.bodyState = bodyState;
        this.content = content;
    }

    public static Body of(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        if (isNullLine(line)) {
            return new Body(BodyState.EMPTY, null);
        }
        return new Body(BodyState.NOT_EMPTY, line);
    }

    private static boolean isNullLine(String line) {
        return Objects.isNull(line);
    }

    public BodyState getState() {
        return bodyState;
    }
}
