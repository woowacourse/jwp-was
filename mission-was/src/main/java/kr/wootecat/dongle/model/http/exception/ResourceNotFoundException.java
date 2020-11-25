package kr.wootecat.dongle.model.http.exception;

public class ResourceNotFoundException extends NotFoundException {

    public ResourceNotFoundException(String resourceName) {
        super(resourceName);
    }
}
