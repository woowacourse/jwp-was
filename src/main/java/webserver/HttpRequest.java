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
        String line = br.readLine();
        String requestLine = line;
        logger.debug("Request Line{}", lineSeparator);
        logger.debug("{}{}", line, lineSeparator);

        StringBuilder header = new StringBuilder();
        while (!line.equals("")) {
            line = br.readLine();
            header.append(lineSeparator);
            header.append(line);

            if (line == null) {
                break;
            }
        }
        logger.debug("Header{}", lineSeparator);
        logger.debug("{}", header);

        return new HttpRequest(requestLine, header.toString());
    }

    public String getUrlPath() {
        String[] tokens = requestLine.split(" ");
        return String.format("./templates%s", tokens[1]);
    }
}
