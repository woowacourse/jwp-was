package webserver.exception;

public class FailedUserJoinException extends RuntimeException {
	public FailedUserJoinException() {
		super("회원 등록을 실패했습니다.");
	}
}
