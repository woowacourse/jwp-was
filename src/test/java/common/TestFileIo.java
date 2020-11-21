package common;

import java.io.*;

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
