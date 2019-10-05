package webserver.exception;

public class NotFoundSessionAttribute extends RuntimeException {
	public NotFoundSessionAttribute() {
		super("해당 Session의 속성을 찾을 수 없습니다.");
	}
}
