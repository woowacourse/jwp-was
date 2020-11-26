package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TestIOUtils {

    private static final String TEST_REQUEST_DIRECTORY = "./src/test/resources/request/";

    public static BufferedReader createRequestBufferedReader(String filename)
        throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(TEST_REQUEST_DIRECTORY + filename));
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}
