package request.header;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class HttpHeaderFields {
    private static final Logger logger = LoggerFactory.getLogger(HttpHeaderFields.class);
    private final HashMap<String, String> headerFields;

    public HttpHeaderFields() {
        this.headerFields = new HashMap<>();
    }

    public void save(BufferedReader bufferedReader) throws IOException {
        String headerField = bufferedReader.readLine();
        while(!headerField.equals("") && !headerField.equals("\n") && headerField!=null) {
            headerField = bufferedReader.readLine();
            saveHeaderFiled(headerField.split(":"));
            logger.debug("HttpHeaderField : {}", headerField);
        }
    }

    private void saveHeaderFiled(String[] splitedHeaderLine) {
        headerFields.put(splitedHeaderLine[0], splitedHeaderLine[1]);
    }
}
