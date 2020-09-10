package utils;

import exception.InvalidFilePathException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestUtils {

    /**
     * transferRequestHeaderToMap은 RequestHeader의 정보를 Map에 담아 반환하는 메서드이다.
     * 현재 RequestHeader 클래스가 별도로 존재하지 않기 때문에, 리팩토링 이전까지는 Map을 통해 관리하고자 한다.
     * RequestHeader의 첫 번째 줄 (예시 : GET /index.html HTTP/1.1) 은 공백으로 분할되어,
     * 각각 Method, Target, Version으로 저장한다.
     * 그 외의 정보는 이름과 값을 나눠 맵에 저장한다.
     *
     * @param requestHeader RequestHeader의 값을 저장한 BufferedReader 객체이다.
     * @return RequestHeader의 정보를 담은 Map<String, String> 객체가 반환된다.
     * @throws IOException BufferedReader의 readLine 연산 도중 오류가 발생하면, IOException을 발생시킨다.
     */
    public static Map<String, String> transferRequestHeaderToMap(BufferedReader requestHeader) throws IOException {
        Map<String,String> requestHeaderInfo = new HashMap<>();
        String[] requestHeaderFirstLine = requestHeader.readLine().split(" ");

        requestHeaderInfo.put("Method",requestHeaderFirstLine[0].trim());
        requestHeaderInfo.put("Target",requestHeaderFirstLine[1].trim());
        requestHeaderInfo.put("Version",requestHeaderFirstLine[2].trim());

        String line = requestHeader.readLine();
        while(!line.isEmpty()) {
            String[] splitLine = line.split(":");
            String key = splitLine[0].trim();
            String value = splitLine[1].trim();

            requestHeaderInfo.put(key, value);
            line = requestHeader.readLine();
        }

        return requestHeaderInfo;
    }

    /**
     * getFilePathInRequest는 Request Header의 요청 대상 정보를 분석하고, 파일 경로를 생성하여 반환한다.
     *
     * @param path RequestHeader의 첫번째 라인에 전달되는 요청 대상 값으로, String 객체가 전달된다.
     * @return 파일의 경로가 String 객체로 반환된다.
     */
    public static String getFilePath(String path) {
        try {
            Objects.requireNonNull(path);
            String locationPath = getLocationPath(path);
            Objects.requireNonNull(locationPath);

            if(path.isEmpty() || locationPath.isEmpty()) {
                throw new ArrayIndexOutOfBoundsException();
            }

            return locationPath + path;
        } catch(ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new InvalidFilePathException();
        }
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
