package exception;

import java.io.UnsupportedEncodingException;

public class ParamDecodeException extends IllegalArgumentException {
    public ParamDecodeException(final UnsupportedEncodingException e) {
        super(e);
    }
}
