package http.supoort.converter.response;

import http.exceptions.FailToConvertMessageException;
import http.model.response.ServletResponse;
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

    public static void convert(ServletResponse response, DataOutputStream dos) {
        try {
            responseStatus(response, dos);
            responseHeader(response, dos);
//            responseBody(response.getBody(), dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new FailToConvertMessageException(e.getMessage());
        }
    }

    private static void responseStatus(ServletResponse response, DataOutputStream dos) throws IOException {
        dos.writeBytes(response.getProtocols().getProtocol() + SPACE);
        dos.writeBytes(response.getHttpStatus().getMessage() + LINE_BREAK);
    }


    private static void responseHeader(ServletResponse response, DataOutputStream dos) throws IOException {
        for (Map.Entry<String, String> entry : response.getHttpHeaders().entrySet()) {
            dos.writeBytes(entry.getKey() + HEAD_SEPARATOR + entry.getValue() + LINE_BREAK);
        }
        dos.writeBytes(LINE_BREAK);
    }

    private static void responseBody(byte[] body, DataOutputStream dos) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }
}
