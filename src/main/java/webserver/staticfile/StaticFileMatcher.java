package webserver.staticfile;

import java.util.Arrays;
import java.util.Optional;

public class StaticFileMatcher {

    public static boolean isStaticFileResourcePath(String resourcePath) {
        Optional<String> result = Arrays.stream(StaticFileType.values())
                .map(x -> x.getFileType())
                .filter(x -> resourcePath.endsWith(x))
                .findAny();
        return result.isPresent();
    }

    public static StaticFileType findStaticFileType(String resourcePath) {
        return Arrays.stream(StaticFileType.values())
                .filter(x -> resourcePath.endsWith(x.getFileType()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("정적 파일 요청이 아닙니다."));
    }

    public static String findStaticFilePath(String resourcePath) {
        StaticFileType staticFileType = findStaticFileType(resourcePath);
        return staticFileType.getPrePath() + resourcePath;
    }
}
