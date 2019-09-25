package controller.exception;

public class PathNotFoundException extends RuntimeException {
    public PathNotFoundException() {
        super("컨트롤 경로를 찾을 수 없습니다.");
    }
}
