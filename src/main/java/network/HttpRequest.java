package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private StartLine startLine;
    private HttpHeader httpHeader;
    private HttpBody httpBody;

    public HttpRequest(final StartLine startLine, final HttpHeader httpHeader, final HttpBody httpBody) {
        this.startLine = startLine;
        this.httpHeader = httpHeader;
        this.httpBody = httpBody;
    }

    public static HttpRequest init(final InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = bufferedReader.readLine();
        StartLine startLine = StartLine.of(line);
        HttpHeader httpHeader = new HttpHeader();

        while (!line.equals("")) {
            String[] headerLine = bufferedReader.readLine().split(": ");
            if (headerLine.length == 1) {
                break;
            }
            httpHeader.addHeader(headerLine[0], headerLine[1]);
        }

        return new HttpRequest(startLine, httpHeader, null);
    }

    public StartLine getStartLine() {
        return startLine;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "startLine=" + startLine +
                ", httpHeader=" + httpHeader +
                ", httpBody=" + httpBody +
                '}';
    }
}