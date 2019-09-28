package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.WebServer;
import webserver.http.Cookie;
import webserver.http.Cookies;
import webserver.http.HttpHeaders;
import webserver.http.HttpStatus;
import webserver.http.request.HttpMethod;
import webserver.http.HttpVersion;
import webserver.http.Pair;
import webserver.http.utils.HttpUtils;
import webserver.http.utils.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.HttpHeaders.HOST;
import static webserver.http.response.HttpResponse.DEFAULT_HTTP_VERSION;

public class HttpTestClient {
    private static final Logger log = LoggerFactory.getLogger(HttpTestClient.class);
    public static final int DEFAULT_PORT = WebServer.DEFAULT_PORT;
    private static final String LOCALHOST = "localhost";

    private final int port;
    private String host;

    public HttpTestClient(int port) {
        this(LOCALHOST, port);
    }

    public HttpTestClient(String host, int port) {
        this.host = host;
        this.port = port;
        WebServer webServer = new WebServer(port);
        Thread thread = new Thread(webServer);
        thread.start();
    }

    public String send(String request) {
        try {
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            out.print(request);
            out.flush();

            List<String> lines = in.lines().collect(Collectors.toList());
            return String.join("\n", lines);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
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

    // todo Cookie 설정 가능하게 추가
    public class HttpRequestBuilder {
        private String method;
        private String uri;
        private String protocolVersion;
        private Map<String, String> headers = new HashMap<>();
        private Map<String, Object> cookies = new HashMap<>();
        private String body = "";

        HttpRequestBuilder(String method) {
            this.method = method;
            this.protocolVersion = DEFAULT_HTTP_VERSION.getHttpVersion();
            headers.put(HOST, "localhost:" + port);
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

        public HttpRequestBuilder addCookie(String key, String value) {
            cookies.put(key, value);
            return this;
        }

        public ResponseSpec exchange() {
            StringBuilder sb = new StringBuilder();

            renderRequestLine(sb);
            renderHeaders(sb);
            renderBody(sb);

            log.debug("\n{}", sb.toString());
            final String response = send(sb.toString());
            return new ResponseSpec(response);
        }

        private void renderRequestLine(final StringBuilder sb) {
            sb.append(String.format("%s %s %s\n", method, uri, protocolVersion));
        }

        private void renderHeaders(final StringBuilder sb) {
            if (StringUtils.isNotEmpty(body)) {
                headers.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length()));
            }
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                sb.append(String.format("%s: %s\n", entry.getKey(), entry.getValue()));
            }

            // cookie
            sb.append(cookies.isEmpty() ? "" : "Cookie: ");
            for (Map.Entry<String, Object> entry : cookies.entrySet()) {
                sb.append(String.format("%s=%s; ", entry.getKey(), entry.getValue()));
            }
            sb.append(cookies.isEmpty() ? "" : "\n");
            sb.append("\n");
        }

        private void renderBody(final StringBuilder sb) {
            sb.append(body.isEmpty() ? "" : body);
        }
    }

    // TODO 리팩토링 많이 필요!`
    public class ResponseSpec {
        private final HttpVersion httpVersion;
        private final HttpStatus httpStatus;
        private final Map<String, String> headers = new HashMap<>();
        private final String body;
        private final Cookies cookies = new Cookies();

        public ResponseSpec(final String response) {
            final String[] split = response.split("\n");

            // response line
            final String[] responseLine = split[0].split(" ");
            this.httpVersion = HttpVersion.of(responseLine[0]);
            this.httpStatus = HttpStatus.from(responseLine[1]);

            // header
            int i = 1;
            String line;
            while (StringUtils.isNotEmpty(line = split[i++])) {
                final Pair pair = HttpUtils.parseHeader(line);

                if (pair.getKey().equals(HttpHeaders.SET_COOKIE)) {
                    final String value = pair.getValue().split(Cookies.DELIMITER)[0];
                    final Pair cookiePair = HttpUtils.parseKeyValue(value, Cookies.DELIMITER_PAIR);
                    cookies.add(new Cookie(cookiePair.getKey(), cookiePair.getValue()));
                } else {
                    headers.put(pair.getKey(), pair.getValue());
                }

                if (i >= split.length) {
                    break;
                }
            }

            // body
            final StringBuilder sb = new StringBuilder();
            while (i < split.length) {
                sb.append(split[i]);
                sb.append("\n");
                i++;
            }
            body = sb.toString();
        }

        public ResponseSpec matchHttpVersion(final HttpVersion expected) {
            assertThat(httpVersion).isEqualTo(expected);
            return this;
        }

        public ResponseSpec matchHttpStatus(final HttpStatus expected) {
            assertThat(httpStatus).isEqualTo(expected);
            return this;
        }

        public ResponseSpec matchHeader(final String key, final String value) {
            assertThat(headers.get(key)).isEqualTo(value);
            return this;
        }

        public ResponseSpec containsBody(final String body) {
            assertThat(this.body.contains(body)).isTrue();
            return this;
        }

        public ResponseSpec matchCookie(final String key, final String value) {
            assertThat(cookies.get(key).getValue()).isEqualTo(value);
            return this;
        }

        public String getCookie(final String key) {
            return cookies.get(key).getValue();
        }
    }
}
