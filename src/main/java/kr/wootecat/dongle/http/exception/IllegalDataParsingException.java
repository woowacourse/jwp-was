package kr.wootecat.dongle.http.exception;

public class IllegalDataParsingException extends RuntimeException {

    private static final String ILLEGAL_PARSE_DATA_EXCEPTION_MESSAGE = "올바른 형식의 데이터가 아니라 Parsing에 실패했습니다.";

    public IllegalDataParsingException() {
        super(ILLEGAL_PARSE_DATA_EXCEPTION_MESSAGE);
    }
}
