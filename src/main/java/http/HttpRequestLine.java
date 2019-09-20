package http;

public class HttpRequestLine {

    private String[] firstLine;

    public HttpRequestLine(String firstLine) {
        String[] splitFirstLine = firstLine.split(" ");
        checkSplitLength(splitFirstLine);
        this.firstLine = splitFirstLine;
    }

    private void checkSplitLength(String[] splitFirstLine) {
        if (splitFirstLine.length != 3) {
            throw new InvalidRequestException("유효하지 않은 요청입니다.");
        }
    }

    public String getMethod() {
        return getInformationFromRequestLine(0);
    }

    private String getInformationFromRequestLine(int index) {
        return firstLine[index];
    }

    public String getUrl() {
        return getInformationFromRequestLine(1);
    }

    public String getProtocol() {
        return getInformationFromRequestLine(2);
    }
}
