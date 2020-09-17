package utils;

import exception.InvalidRequestPathException;
import web.FileType;

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
            String resourcePath = FileType.findFileType(path).getResourcePath();
            Objects.requireNonNull(resourcePath);

            if(path.isEmpty() || resourcePath.isEmpty()) {
                throw new ArrayIndexOutOfBoundsException();
            }

            return resourcePath + path;
        } catch(ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new InvalidRequestPathException();
        }
    }
}
