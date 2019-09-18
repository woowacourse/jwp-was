package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private String uri;
    private HttpMethod method;
    private HttpHeader headers;
    private RequestParameter requestParameter;
    private String body;

    public HttpRequest(InputStream requestStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(requestStream));
            List<String> lines = new ArrayList<>();

            String line = parseStartLine(br);
            lines.add(line);

            List<String> headers = parseHeaders(br);
            lines.addAll(headers);

            if (this.headers.getContentLength() > 0) {
                List<String> bodies = parseBody(br);
                lines.addAll(bodies);
            }

            log.info(String.join("\n", lines));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String parseStartLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] tokens = line.split(" ");
        method = HttpMethod.of(tokens[0]);
        if(tokens[1].contains("?")) {
            String[] path = tokens[1].split("\\?");
            uri = path[0];
            requestParameter = new RequestParameter(parseQueryString(path[1]));
            return line;
        }
        uri = tokens[1];
        return line;
    }

    private List<String> parseHeaders(BufferedReader br) throws IOException {
        List<String> headers = new ArrayList<>();
        String header = br.readLine();
        while (!"".equals(header)) {
            if (header == null) {
                break;
            }
            headers.add(header);
            header = br.readLine();
        }
        this.headers = new HttpHeader(headers);
        return headers;
    }

    private List<String> parseBody(BufferedReader br) throws IOException {
        List<String> bodies = new ArrayList<>();
        String line = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (!"".equals(line)) {
            if (line == null) {
                break;
            }
            bodies.add(line);
            sb.append(line);
            line = br.readLine();
        }
        body = sb.toString();
        return bodies;
    }

    private Map<String, String> parseQueryString(String queryString) {
        return Arrays.stream(queryString.split("&"))
                .map(query -> query.split("="))
                .collect(Collectors.toMap(q -> q[0], q -> q[1]))
                ;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getHeader(String key) {
        return headers.getValue(key);
    }

    public String getBody() {
        return body;
    }

    public String getParameter(String key) {
        return requestParameter.getParameter(key);
    }
}
