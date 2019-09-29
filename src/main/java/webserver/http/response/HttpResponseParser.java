package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.exception.IntervalServerException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponseParser {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseParser.class);

    public static void send(final DataOutputStream dataOutputStream, final HttpResponse httpResponse) {
        try {
            responseHeader(dataOutputStream, httpResponse);
            writeResponseBody(dataOutputStream, httpResponse);
            dataOutputStream.flush();
        }  (IOException e) {
            logger.error("{}", e);
            throw new IntervalServerException();
        }
    }

    private static void writeResponseBody(final DataOutputStream dataOutputStream, final HttpResponse httpResponse) throws IOException {
        if (httpResponse.getHttpResponseBody().getLength() != 0) {
            responseBody(dataOutputStream, httpResponse);
        }
    }

    private static void responseHeader(final DataOutputStream dataOutputStream, final HttpResponse httpResponse) throws IOException {
        dataOutputStream.writeBytes(httpResponse.getStatusLine() + "\r\n");

        for (Map.Entry<String, String> element : httpResponse.getHttpHeader().getHeaders().entrySet()) {
            dataOutputStream.writeBytes(element.getKey() + ": " + element.getValue() + "\r\n");
        }
        dataOutputStream.writeBytes("\r\n");
    }

    private static void responseBody(final DataOutputStream dataOutputStream, final HttpResponse httpResponse) throws IOException {
        dataOutputStream.write(httpResponse.getHttpResponseBody().getBody(), 0, httpResponse.getHttpResponseBody().getLength());
    }
}
