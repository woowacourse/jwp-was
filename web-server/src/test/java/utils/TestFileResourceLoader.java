package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestFileResourceLoader {

    private static final String TEST_FILE_DIRECTORY = "./src/test/resources/";

    public static InputStream fetchTestFile(String filename) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(new File(TEST_FILE_DIRECTORY + filename)));
    }
}
