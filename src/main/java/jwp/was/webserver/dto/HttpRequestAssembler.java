package jwp.was.webserver.dto;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jwp.was.webserver.FileNameExtension;
import jwp.was.webserver.HttpMethod;
import jwp.was.webserver.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestAssembler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestAssembler.class);
    private static final String LINE_DELIMITER = " ";
    private static final String EMPTY = "";
    private static final int HTTP_METHOD_INDEX_OF_REQUEST_LINE = 0;
    private static final int URL_INDEX_OF_REQUEST_LINE = 1;
    private static final int PROTOCOL_INDEX_OF_REQUEST_LINE = 2;

    public static HttpRequest assemble(BufferedReader br) throws IOException {
        String line = readLine(br);
        String[] requestLine = line.split(LINE_DELIMITER);

        HttpMethod httpMethod = HttpMethod.from(requestLine[HTTP_METHOD_INDEX_OF_REQUEST_LINE]);
        UrlPath urlPath = UrlPath.from(requestLine[URL_INDEX_OF_REQUEST_LINE]);
        Parameters parameters = Parameters.fromUrl(requestLine[URL_INDEX_OF_REQUEST_LINE]);
        Protocol protocol = Protocol.of(requestLine[PROTOCOL_INDEX_OF_REQUEST_LINE]);
        Headers headers = Headers.from(readHeaders(br));

        if (httpMethod.hasRequestBody()) {
            String body = readBody(br, headers);
            parameters = Parameters.combine(parameters, Parameters.fromEncodedParameter(body));
        }
        FileNameExtension fileNameExtension = FileNameExtension.from(urlPath.getUrlPath());

        return new HttpRequest(httpMethod, urlPath, parameters, protocol, headers, fileNameExtension
        );
    }

    private static String readLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        LOGGER.debug("request line : {}", line);
        return line;
    }

    private static List<String> readHeaders(BufferedReader br) throws IOException {
        List<String> headers = new ArrayList<>();

        while (true) {
            String header = br.readLine();
            if (header.equals(EMPTY)) {
                break;
            }
            LOGGER.debug("header : {}", header);
            headers.add(header);
        }
        return headers;
    }

    private static String readBody(BufferedReader br, Headers headers) throws IOException {
        int contentLength = Integer.parseInt(headers.get(CONTENT_LENGTH));
        String body = IOUtils.readData(br, contentLength);
        LOGGER.debug("body : {}", body);
        return body;
    }
}
