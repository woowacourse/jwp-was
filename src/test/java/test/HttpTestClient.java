package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.WebServer;
import webserver.http.request.HttpMethod;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static webserver.http.HttpHeaders.HOST;
import static webserver.http.response.HttpResponse.DEFAULT_HTTP_VERSION;

public class HttpTestClient {
    private static final Logger log = LoggerFactory.getLogger(HttpTestClient.class);

    private static final String LOCALHOST = "localhost";

    private final BufferedReader in;
    private final PrintWriter out;
    private final int port;

    public HttpTestClient(int port)  {
        this(LOCALHOST, port);
    }

    public HttpTestClient(String host, int port)  {
        try {
            this.port = port;
            WebServer webServer = new WebServer(port);
            Thread thread = new Thread(webServer);
            thread.start();

            Socket socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        }catch (IOException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public String send(String request) {
        out.print(request);
        out.flush();

        List<String> lines = in.lines().collect(Collectors.toList());
        return String.join("\n", lines);
    }

    public HttpRequestBuilder get() {
        return new HttpRequestBuilder(HttpMethod.GET.name());
    }

    public HttpRequestBuilder post() {
        return new HttpRequestBuilder(HttpMethod.POST.name());
    }

    public HttpRequestBuilder put() {
        return new HttpRequestBuilder(HttpMethod.PUT.name());
    }

    public HttpRequestBuilder delete() {
        return new HttpRequestBuilder(HttpMethod.DELETE.name());
    }

    public class HttpRequestBuilder {
        private String method;
        private String uri;
        private String protocolVersion;
        private Map<String, String> headers = new HashMap<>();
        private String body;

        HttpRequestBuilder(String method) {
            this.method = method;
            this.protocolVersion = DEFAULT_HTTP_VERSION;
            headers.put(HOST, "localhost: " + port);
        }

        public HttpRequestBuilder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public HttpRequestBuilder protocolVersion(String protocolVersion) {
            this.protocolVersion = protocolVersion;
            return this;
        }

        public HttpRequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequestBuilder addHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        // TODO Response 로 받아서 테스트할 수 있게 바꾸기
        public String exchange() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s %s %s\n", method, uri, protocolVersion));
            for (final String key : headers.keySet()) {
                sb.append(String.format("%s: %s\n", key, headers.get(key)));
            }
            sb.append("\n");
            sb.append(body);

            log.debug("\n" + sb.toString());
            return send(sb.toString());
        }
    }
}
