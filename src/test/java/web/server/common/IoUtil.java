package web.server.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import web.server.domain.request.HttpRequest;

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

    public static HttpRequest createRequest(String url) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + url));
        return new HttpRequest(
            new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
    }
}
