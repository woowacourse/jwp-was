package webserver;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final String lineSeparator = System.getProperty("line.separator");
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final String requestLine;
    private final String header;

    public HttpRequest(String requestLine, String header) {
        this.requestLine = requestLine;
        this.header = header;
    }

    public static HttpRequest of(BufferedReader br) throws IOException {
        StringBuilder requestLine = new StringBuilder("Request Line");
        String line = br.readLine();
        requestLine.append(lineSeparator);
        requestLine.append(line);
        requestLine.append(lineSeparator);
        logger.debug("{}", requestLine);

        StringBuilder header = new StringBuilder("Header");
        while (!line.equals("")) {
            header.append(lineSeparator);
            line = br.readLine();
            header.append(line);

            if (line == null) {
                break;
            }
        }
        logger.debug("{}", header);

        return new HttpRequest(requestLine.toString(), header.toString());
    }
}
