package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerNotFoundException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(ControllerNotFoundException.class);

    public ControllerNotFoundException(String requestedUri) {
        log.error("Controller Not Found - Requested Uri: {}", requestedUri);
    }
}
