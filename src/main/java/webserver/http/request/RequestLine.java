package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.Protocol;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);
    private static final String SPACE = "\\s";

    private final Method method;
    private final Uri uri;
    private final Protocol protocol;

    private RequestLine(Method method, Uri uri, Protocol protocol) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
    }

    public static RequestLine of(BufferedReader bufferedReader) throws IOException {
        String source = bufferedReader.readLine();
        logger.debug(String.format("requestLine : %s", source));

        String[] sources = source.split(SPACE);
        Method method = Method.of(sources[0]);
        Uri uri = Uri.of(sources[1]);
        Protocol protocol = Protocol.of(sources[2]);

        return new RequestLine(method, uri, protocol);
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return uri.getPath();
    }

    public Map<String, String> getParameters() {
        return uri.getParameters();
    }
}
