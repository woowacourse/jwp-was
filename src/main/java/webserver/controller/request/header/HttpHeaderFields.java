package webserver.controller.request.header;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class HttpHeaderFields {
    private static final Logger logger = LoggerFactory.getLogger(HttpHeaderFields.class);
    private static final String HTTP_HEADER_FIELD_DELEMETER = ": ";
    private final HashMap<String, String> headerFields;

    public HttpHeaderFields(BufferedReader bufferedReader) throws IOException {
        this.headerFields = new HashMap<>();
        String headerField = bufferedReader.readLine();
        save(bufferedReader, headerField);
    }

    private void save(BufferedReader bufferedReader, String headerField) throws IOException {
        while (headerField != null && !headerField.equals("") && !headerField.equals("\n")) {
            saveHeaderFiled(headerField.split(HTTP_HEADER_FIELD_DELEMETER));
            logger.debug("HttpHeaderField : {}", headerField);
            headerField = bufferedReader.readLine();
        }
    }

    private void saveHeaderFiled(String[] splitedHeaderLine) {
        headerFields.put(splitedHeaderLine[0], splitedHeaderLine[1]);
    }

    public String readData(BufferedReader bufferedReader) throws IOException {
        return IOUtils.readData(bufferedReader, getContentsLength());
    }

    private int getContentsLength() {
        return Integer.parseInt(headerFields.get("Content-Length"));
    }
}
