package http.parser;

import http.request.RequestBody;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBodyParser {

    public static RequestBody parse(BufferedReader br, String contentLength) throws IOException {
        String body = IOUtils.readData(br, Integer.parseInt(contentLength));
        return new RequestBody(QueryParamsParser.parse(body));
    }
}
