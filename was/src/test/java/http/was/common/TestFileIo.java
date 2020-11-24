package http.was.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class TestFileIo {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    public static OutputStream createOutputStream(String fileName) throws FileNotFoundException {
        return new FileOutputStream(new File(TEST_DIRECTORY + fileName));
    }

    public static BufferedReader readBufferedReader(String fileName) throws FileNotFoundException {
        File file = new File(TEST_DIRECTORY + fileName);
        InputStream in = new FileInputStream(file);
        return new BufferedReader(new InputStreamReader(in));
    }
}
