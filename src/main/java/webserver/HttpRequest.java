package webserver;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final List<String> requestContents = new ArrayList<>();

    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;


    public HttpRequest(InputStream in) {
        try {
            inputStreamReader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            do {
                line = bufferedReader.readLine();

                if (line == null || line.equals("")) {
                    break;
                }

                logger.info("request contents: {}", line);

                requestContents.add(line);

            } while (true);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    public String getRequestContents() {
        return StringUtils.join(requestContents, '\n');
    }

    public RequestMethod getMethod() {
        return RequestMethod.GET;
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

    public String getPath() {
        String[] tokens = requestContents.get(0).split(" ");
        return tokens[1].split("\\?")[0];
    }

    public String getQueryString() {
        String[] tokens = requestContents.get(0).split(" ");
        try {
            return URLDecoder.decode(tokens[1].split("\\?")[1], StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported");
        }
    }
}
