package utils;

import exception.InvalidHttpRequestException;

import java.util.Objects;

public class RequestUtils {

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
            throw new InvalidHttpRequestException();
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
