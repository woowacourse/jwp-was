package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import http.HttpRequestBody;
import http.HttpRequestHeader;
import http.HttpRequestLine;
import http.HttpRequestParam;
import utils.FileIoUtils;
import utils.HttpRequestUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // Line 은 여기서 계속 읽는다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            // HttpRequestLine 을 만들고
            HttpRequestLine httpRequestLine = getHttpRequestLine(bufferedReader);
            logger.debug("Http Request Line : {}", httpRequestLine);

            HttpRequestParam httpRequestParam = getHttpRequestParams(httpRequestLine);
            logger.debug("Http Request Line : {}", httpRequestParam);

            HttpRequestHeader httpRequestHeader = getHttpRequestHeader(bufferedReader);
            logger.debug("Http Request Header : {}", httpRequestHeader);

            HttpRequestBody httpRequestBody = getHttpRequestBody(bufferedReader, httpRequestHeader.getContentLength());
            logger.debug("Http Request Body : {}", httpRequestBody);

            // 여기서 static file 들을 모두 처리하도록 만들자.
            // enum MimeType 을 만들어서 확장자로 MimeType 을 생성하자.

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequestLine.getUrl().getPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequestLine getHttpRequestLine(BufferedReader bufferedReader) throws IOException {
        String firstLine = bufferedReader.readLine();
        return new HttpRequestLine(firstLine);
    }

    private HttpRequestParam getHttpRequestParams(HttpRequestLine httpRequestLine) {
        Map<String, String> params = HttpRequestUtils.parse(httpRequestLine.getUrl());
        return new HttpRequestParam(params);
    }

    private HttpRequestHeader getHttpRequestHeader(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (!"".equals(line) && line != null) {
            lines.add(line);
            line = bufferedReader.readLine();
        }
        Map<String, String> headers = HttpRequestUtils.parse(lines);
        return new HttpRequestHeader(headers);
    }

    private HttpRequestBody getHttpRequestBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        String bodyData = IOUtils.readData(bufferedReader, contentLength);
        Map<String, String> body = HttpRequestUtils.parse(bodyData);
        return new HttpRequestBody(body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
