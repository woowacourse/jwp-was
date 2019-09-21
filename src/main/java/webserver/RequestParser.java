package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class RequestParser {
    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);

    private Socket connection;

    public RequestParser(Socket connection) {
        this.connection = connection;
    }

    public String[] parse() throws IOException {
        try (InputStream inputStream = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8" ));
            String headerLine = bufferedReader.readLine();
            String[] firstHeaderLine = headerLine.split(" ");

            while(!headerLine.equals("") && !headerLine.equals("\n") && headerLine!=null) {
                headerLine = bufferedReader.readLine();
                saveHeaderFiled(headerLine.split(":"));
                logger.debug("RequestParser : {}", headerLine);
            }
            
        }
    }

    private void saveHeaderFiled(String[] splitedHeaderLine) {
        HashMap<String, String> headerFields = new HashMap<>();
        headerFields.put(splitedHeaderLine[0], splitedHeaderLine[1]);
    }
}
