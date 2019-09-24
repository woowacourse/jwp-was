package utils;

public class LoadingFileFailException extends RuntimeException {
    public LoadingFileFailException() {
        super("해당 파일을 읽을 수 없습니다. (존재하지 않거나 권한이 없습니다.)");
    }

    private LoadingFileFailException(String filePath) {
        super(String.format("[] 해당 경로의 파일을 읽을 수 없습니다. (존재하지 않거나 권한이 없습니다.)"));
    }

    public static LoadingFileFailException fromFilePath(String filePath) {
        return new LoadingFileFailException(filePath);
    }
}
