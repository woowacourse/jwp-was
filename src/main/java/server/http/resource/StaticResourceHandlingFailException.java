package server.http.resource;

public class StaticResourceHandlingFailException extends RuntimeException {
    public StaticResourceHandlingFailException() {
        super("정적 파일을 불러오는 데 실패했습니다.");
    }
}
