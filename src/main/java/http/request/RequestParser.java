package http.request;

import java.io.BufferedReader;
import java.io.IOException;

import utils.IOUtils;

public class RequestParser {

    public static Request parse(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] lines = line.split(" ");
        RequestLine requestLine = new RequestLine(lines[0], lines[1], lines[2]);

        RequestHeader requestHeader = new RequestHeader();
        String[] headers;
        line = br.readLine();

        while (!line.equals("")) {
            System.out.println("line 출력합니다. : " + line);
            headers = line.split(": ");
            requestHeader.add(headers[0], headers[1]);
            line = br.readLine();
        }

        if (br.ready()) {
            int contentLength = Integer.parseInt(requestHeader.getValue("Content-Length"));
            String data = IOUtils.readData(br, contentLength);
            RequestBody requestBody = new RequestBody(data);
            return new Request(requestLine, requestHeader, requestBody);
        }

        return new Request(requestLine, requestHeader, RequestBody.emptyData());
    }
}
