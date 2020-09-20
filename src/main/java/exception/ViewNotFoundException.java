package exception;

public class ViewNotFoundException extends HttpRequestException {
    public ViewNotFoundException(final String viewName) {
        super(viewName + "을 찾을 수 없습니다.");
    }
}
