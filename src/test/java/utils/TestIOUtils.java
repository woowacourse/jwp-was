package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TestIOUtils {

    private static final String TEST_REQUEST_DIRECTORY = "./src/test/resources/request/";
    private static final String TEST_RESPONSE_DIRECTORY = "./src/test/resources/response/";

    public static String convertText(BufferedReader bufferedReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while (!Objects.isNull(line = bufferedReader.readLine())) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static BufferedReader createRequestBufferedReader(String filename) throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(TEST_REQUEST_DIRECTORY + filename));
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }

    public static OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(TEST_RESPONSE_DIRECTORY + filename));
    }

    public static BufferedReader createResponseBufferedReader(String filename) throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(TEST_RESPONSE_DIRECTORY + filename));
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}
