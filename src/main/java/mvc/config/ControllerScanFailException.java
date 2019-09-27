package mvc.config;

public class ControllerScanFailException extends RuntimeException {
    public ControllerScanFailException() {
        super("Failed to instantiate annotation-scanned controllers.");
    }
}
