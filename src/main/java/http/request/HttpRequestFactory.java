package http.request;

import http.exception.CanNotParseDataException;
import http.request.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpRequestFactory {
    public static HttpRequest parseHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        List<Object> firstLineTokens = parseFirstLine(br);
        RequestHeader headers = parseHeader(br);
        Map<String, String> data = parseData(firstLineTokens, br, headers);

        return new HttpRequest(firstLineTokens, headers, data);
    }

    private static List<Object> parseFirstLine(BufferedReader br) throws IOException {
        String[] tokens = br.readLine().split(" ");

        return Arrays.asList(
                RequestMethod.of(tokens[0]),
                new RequestPath(RequestPrefixPath.of(tokens[1]), tokens[1]),
                RequestVersion.of(tokens[2])
        );
    }

    private static RequestHeader parseHeader(BufferedReader br) throws IOException {
        List<String> parseHeaderLines = new ArrayList<>();
        String headerLine = br.readLine();

        while (!"".equals(headerLine)) {
            parseHeaderLines.add(headerLine);
            headerLine = br.readLine();
        }

        return new RequestHeader(parseHeaderLines);
    }

    private static Map<String, String> parseData(List<Object> firstLineTokens, BufferedReader br, RequestHeader headers) throws IOException {
        RequestMethod method = (RequestMethod) firstLineTokens.get(0);
        RequestPath url = (RequestPath) firstLineTokens.get(1);

        if (method.isGet()) {
            return url.getFullPath().contains("?") ? new RequestData(url).getData() : Collections.emptyMap();
        }

        if (method.isPost()) {
            String contentLength = headers.getContentLength();
            String bodyData = IOUtils.readData(br, Integer.parseInt(contentLength));
            return new RequestData(bodyData).getData();
        }

        throw new CanNotParseDataException();
    }

}
