package servlet.controller.exception;

public class Page404NotFoundException extends RuntimeException {
    private static final String MESSAGE_OF_404_ERROR = "404 Error\n" + "해당 요청에 대한 페이지를 찾을 수 없습니다.";

    public Page404NotFoundException() {
        super(MESSAGE_OF_404_ERROR);
    }
}
