package http.response;

import http.HTTP;

public class StatusLine {
    private ResponseStatus responseStatus;
    private static final String FIRST_LINE_DELIMITER = " ";


    public StatusLine() {
        this.responseStatus = ResponseStatus.NOT_FOUND;
    }


    public StatusLine(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponse() {
        StringBuffer sb = new StringBuffer();
        sb.append(HTTP.VERSION.getPhrase()).append(FIRST_LINE_DELIMITER).append(responseStatus.getInfo());
        return sb.toString();
    }
}
