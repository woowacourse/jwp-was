package webserver.request;

import utils.IOUtils;
import webserver.EntityHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String PARAMETER_DELIMITER = "&";
    private static final String NAME_VALUE_DELIMITER = "=";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int NAME_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int REQUEST_LINE_LENGTH = 3;
    private static final String HEADER_DELIMITER = ": ";

    private final RequestLine requestLine;
    private final RequestHeaders headers;
    private final RequestParameters parameters;

    public HttpRequest(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream이 null 입니다.");
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        this.requestLine = createRequestLine(bufferedReader);
        this.headers = new RequestHeaders(parsingHeaders(bufferedReader));
        this.parameters = new RequestParameters(parsingParameters(bufferedReader));
    }

    private RequestLine createRequestLine(BufferedReader bufferedReader) {
        String requestLine = readLine(bufferedReader);
        String[] tokens = requestLine.split(REQUEST_LINE_DELIMITER);
        if (tokens.length != REQUEST_LINE_LENGTH) {
            throw new RuntimeException("잘못된 RequetLine입니다." + requestLine);
        }
        HttpMethod httpMethod = HttpMethod.from(tokens[HTTP_METHOD_INDEX]);
        String resource = tokens[VALUE_INDEX];
        return RequestLine.of(httpMethod, resource);
    }

    private Map<String, String> parsingHeaders(BufferedReader bufferedReader) {
        Map<String, String> headers = new HashMap<>();
        String line = readLine(bufferedReader);
        while ((line != null) && (!line.isEmpty())) {
            System.out.println(line);
            String[] tokens = line.split(HEADER_DELIMITER);
            headers.put(tokens[NAME_INDEX], tokens[VALUE_INDEX]);
            line = readLine(bufferedReader);
        }
        return headers;
    }

    private Map<String, String> parsingParameters(BufferedReader bufferedReader) {
        String body = extractBody(bufferedReader);
        if (body == null) {
            return null;
        }
        return Arrays.stream(body.split(PARAMETER_DELIMITER))
                .map(token -> token.split(NAME_VALUE_DELIMITER))
                .collect(Collectors.toMap(parameter -> parameter[NAME_INDEX], parameter -> parameter[VALUE_INDEX]));
    }

    private String extractBody(BufferedReader bufferedReader) {
        if (headers.isNull(EntityHeader.CONTENT_LENGTH)) {
            return requestLine.getData();
        }
        String contentLength = headers.getValue(EntityHeader.CONTENT_LENGTH.get());
        String body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));
        if (requestLine.isNotExistData()) {
            return body;
        }
        return String.join(PARAMETER_DELIMITER, body, requestLine.getData());
    }

    private String readLine(BufferedReader bufferedReader) {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("bufferedReader IOException In HttpRequest.readLine");
        }
    }

    public boolean isMatchHttpMethod(HttpMethod httpMethod) {
        return requestLine.isMatchHttpMethod(httpMethod);
    }

    public boolean containsPath(List<String> paths) {
        return paths.stream()
                .anyMatch(path -> requestLine.containsPath(path));
    }

    public String getMethod() {
        return this.requestLine.getMethod();
    }

    public String getPath() {
        return this.requestLine.getPath();
    }

    public String getHeader(String header) {
        return this.headers.getValue(header);
    }

    public String getParameter(String parameter) {
        return this.parameters.getValue(parameter);
    }

    public Set<String> getParametersKeys() {
        return this.parameters.getKeys();
    }
}
