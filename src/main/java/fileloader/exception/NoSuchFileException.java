package fileloader.exception;

import utils.StringUtils;

public class NoSuchFileException extends RuntimeException {
    private static final String ERROR_MESSAGE_FORMAT = "%s는 존재하지 않는 파일입니다.";

    public NoSuchFileException(String message) {
        super(getErrorMessage(message));
    }

    private static String getErrorMessage(String message) {
        if (StringUtils.isNull(message)) {
            message = "NULL";
        }

        return String.format(ERROR_MESSAGE_FORMAT, message);
    }
}
