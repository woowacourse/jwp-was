package web.server.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class IoUtil {

    private static final String testDirectory = "./src/test/java/resources/";

    public static DataOutputStream createOutputStream(String filename) throws FileNotFoundException {
        new File(testDirectory + "/out").mkdirs();
        return new DataOutputStream(new FileOutputStream(new File(testDirectory + filename)));
    }

    public static String readFile(String path) throws IOException {
        File file = new File(testDirectory + path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
