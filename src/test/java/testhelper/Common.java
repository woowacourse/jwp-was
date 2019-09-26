package testhelper;

import java.io.*;

public class Common {
    private static final String TEST_DIRECTORY_PATH = "./src/test/resources/";

    public static BufferedReader getBufferedReader(final String path) throws FileNotFoundException {
        String absolutePath = TEST_DIRECTORY_PATH + path;

        InputStream inputStream = new FileInputStream(new File(absolutePath));
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }
}
