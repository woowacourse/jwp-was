package exceptions;

public class FailToRenderView extends RuntimeException {
    public FailToRenderView() {
    }

    public FailToRenderView(String message) {
        super(message);
    }
}
