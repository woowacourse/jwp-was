package utils;

import exception.InvalidRequestPathException;

import java.util.Objects;

public class URIUtils {

    /**
     * getFilePathInRequest는 전달받은 파일 경로가 유효한지 확인하고, 파일 경로를 생성하여 반환한다.
     *
     * @exception InvalidRequestPathException 유효하지 않은 경로가 전달될 시 발생한다.
     * @param path Request에 포함되어 전달되는 파일 경로 값으로, String 객체가 전달된다.
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
            throw new InvalidRequestPathException();
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
        String fileName = filePath.split("\\.")[0];
        String fileType = filePath.split("\\.")[1];
        if(fileName.isEmpty() || filePath.isEmpty()){
            throw new ArrayIndexOutOfBoundsException();
        }
        if(fileType.equals("html")) {
            return "./templates";
        }
        return "./static";
    }
}
