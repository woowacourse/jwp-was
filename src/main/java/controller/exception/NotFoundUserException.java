package controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundUserException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(NotFoundUserException.class);

    public NotFoundUserException(String requestedUserId) {
        logger.debug("Requested User Id: {}", requestedUserId);
    }
}
