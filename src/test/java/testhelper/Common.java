package testhelper;

import java.io.*;

public class Common {
    private static final String TEST_DIRECTORY_PATH = "./src/test/resources/";

    public static BufferedReader getBufferedReader(final String path) throws FileNotFoundException {

        InputStreamReader inputStreamReader = new InputStreamReader(getInputStream(path));
        return new BufferedReader(inputStreamReader);
    }

    public static InputStream getInputStream(final String path) throws FileNotFoundException {
        String absolutePath = TEST_DIRECTORY_PATH + path;

        return new FileInputStream(new File(absolutePath));
    }
}
