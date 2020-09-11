package utils;

public class FileNotExitsException extends RuntimeException {

    public FileNotExitsException(String filePath) {
        super("[" + filePath + "] Path에 해당하는 파일이 존재하지 않습니다.");
    }
}
