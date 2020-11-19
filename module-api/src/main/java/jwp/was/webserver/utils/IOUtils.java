package jwp.was.webserver.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {

    /**
     * @param br            Request Body를 시작하는 시점
     * @param contentLength Request Header의 Content-Length 값
     * @return body 응답
     * @throws IOException 읽지 못할 경우 예외 발생
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }
}
