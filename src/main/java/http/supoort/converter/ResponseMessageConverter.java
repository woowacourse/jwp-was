package http.supoort.converter;

import http.exceptions.FailToConvertMessageException;
import http.model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class ResponseMessageConverter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseMessageConverter.class);
    private static final String SPACE = " ";
    private static final String HEAD_SEPARATOR = ":" + SPACE;
    private static final String LINE_BREAK = "\r\n";

    public static void convert(HttpResponse httpResponse, DataOutputStream dos) {
        try {
            responseStatus(httpResponse, dos);
            responseHeader(httpResponse, dos);
            responseBody(httpResponse.getBody(), dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new FailToConvertMessageException(e.getMessage());
        }
    }

    private static void responseStatus(HttpResponse httpResponse, DataOutputStream dos) throws IOException {
        dos.writeBytes(httpResponse.getProtocol().getProtocol() + SPACE);
        dos.writeBytes(httpResponse.getHttpStatus().getMessage() + LINE_BREAK);
    }


    private static void responseHeader(HttpResponse httpResponse, DataOutputStream dos) throws IOException {
        for (Map.Entry<String, String> entry : httpResponse.getHeaders().entrySet()) {
            dos.writeBytes(entry.getKey() + HEAD_SEPARATOR + entry.getValue() + LINE_BREAK);
        }
        dos.writeBytes(LINE_BREAK);
    }

    private static void responseBody(byte[] body, DataOutputStream dos) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
