package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class IOUtils {

    public static final String NEW_LINE = "\n";

    /**
     * @param BufferedReader는 Request Body를 시작하는 시점이어야
     * @param contentLength는  Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static String readHeader(BufferedReader bufferedReader) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line = bufferedReader.readLine();

        while (!"".equals(line) && line != null) {
            sb.append(line).append(NEW_LINE);
            line = bufferedReader.readLine();
        }
        return sb.toString();
    }

    public static String readBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        if (contentLength == 0) {
            return "";
        }
        return readData(bufferedReader, contentLength);
    }

    public static void addParameters(String query, Map<String, String> params) throws UnsupportedEncodingException {
        String[] infos = query.split("&");
        for (String info : infos) {
            String[] keyAndValue = info.split("=");
            params.put(keyAndValue[0], URLDecoder.decode(keyAndValue[1], StandardCharsets.UTF_8.toString()));
        }
    }
}
