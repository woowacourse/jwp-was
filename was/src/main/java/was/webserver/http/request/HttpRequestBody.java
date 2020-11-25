package was.webserver.http.request;

import was.utils.IOUtils;
import was.webserver.http.BodyState;
import was.webserver.http.Parameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class HttpRequestBody {
    private final BodyState bodyState;
    private final Parameters parameters;

    private HttpRequestBody(BodyState bodyState, Parameters parameters) {
        this.bodyState = bodyState;
        this.parameters = parameters;
    }

    public static HttpRequestBody of(BufferedReader bufferedReader, String contentLength) throws IOException {
        String body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));
        return new HttpRequestBody(BodyState.NOT_EMPTY, Parameters.notEmptyQueryParameters(body));
    }

    private static boolean isNullLine(String line) {
        return Objects.isNull(line);
    }

    public static HttpRequestBody of(byte[] body) {
        String content = new String(body, StandardCharsets.UTF_8);
        if (isNullLine(content)) {
            return new HttpRequestBody(BodyState.EMPTY, null);
        }
        return new HttpRequestBody(BodyState.NOT_EMPTY, Parameters.notEmptyQueryParameters(content));
    }

    public static HttpRequestBody emptyRequestBody() {
        return new HttpRequestBody(BodyState.EMPTY, null);
    }

    public BodyState getState() {
        return bodyState;
    }

    public Parameters getParameters() {
        return parameters;
    }
}
