package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestUtils {

    /**
     * getFilePathInRequest는 Request Header를 분석하고, 거기에서 파일의 이름을 추출해 반환한다.
     * Request Header의 첫번째 줄에 [GET /poo.html HTTP/1.1] 과 같은 형식으로 파일명을 포함한 정보가 전달된다.
     * 이를 공백을 기준으로 split하고, 그 중 2번째 값(상단의 예시에서는 /poo.html)을 얻어 반환하도록 설계되었다.
     *
     * @param requestHeader RequestHeader의 값으로, BufferedReader 객체가 전달된다.
     * @return 파일의 경로가 String 객체로 반환된다.
     * @throws IOException BufferReader 객체인 requestHeader를 읽는 과정에서 오류 발생 시 IOException이 throw된다.
     */
    public static String getFilePathInRequestHeader(BufferedReader requestHeader) throws IOException {
        String line = requestHeader.readLine();
        String path = line.split(" ")[1];
        return getLocationPath(path) + path;
    }

    /**
     * getLocationPath는 filePath를 전달받아, 파일의 형식에 맞는 경로를 반환한다.
     * 만약 파일이 html 파일이라면 templates를, 그렇지 않다면 static을 경로로 제시한다.
     *
     * @param filePath 파일의 경로로, String 객체가 전달된다.
     * @return 파일 경로 앞에 붙어야 할 올바른 경로값을 반환한다.
     */
    private static String getLocationPath(String filePath) {
        String fileType = filePath.split("\\.")[1];
        if(fileType.equals("html")) {
            return "./templates";
        }
        return "./static";
    }
}
