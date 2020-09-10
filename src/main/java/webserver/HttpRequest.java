package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.IOUtils;

public class HttpRequest {
    private static final String CONTENT_LENGTH = "Content-Length";

    private enum HttpMethod {
        GET, POST;

        public boolean isPost() {
            return this == POST;
        }
    }

    private final HttpMethod httpMethod;
    private final String path;
    private final Map<String, String> parameters;
    private final Map<String, String> httpHeaders;
    private final String body;

    private HttpRequest(HttpMethod httpMethod, String path, Map<String, String> parameters,
            Map<String, String> httpHeaders, String body) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.parameters = parameters;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public static HttpRequest from(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        String[] tokens = line.split(" ");
        HttpMethod httpMethod = HttpMethod.valueOf(tokens[0]);
        String[] urlAndParams = tokens[1].split("\\?");
        String path = urlAndParams[0];
        Map<String, String> params = new HashMap<>();

        if (urlAndParams.length > 1) {
            String[] paramTokens = urlAndParams[1].split("&");
            for (String paramToken : paramTokens) {
                String[] strings = paramToken.split("=");
                params.put(strings[0], strings[1]);
            }
        }

        Map<String, String> httpsHeaders = new HashMap<>();
        httpsHeaders.put("Http-Version", tokens[2]);
        httpsHeaders.putAll(IOUtils.readRequestHeader(bufferedReader));

        String body = null;

        if (httpMethod.isPost()) {
            body = IOUtils.readData(bufferedReader,
                    Integer.parseInt(httpsHeaders.get(CONTENT_LENGTH)));
        }

        return new HttpRequest(httpMethod, path, params, httpsHeaders, body);
    }
}
