package db;

public class NotFoundEntityException extends RuntimeException {

    public NotFoundEntityException() {
        super("해당하는 엔티티를 찾지 못했습니다.");
    }
}
