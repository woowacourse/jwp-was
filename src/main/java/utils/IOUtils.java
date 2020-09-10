package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.Request;
import http.RequestBody;
import http.RequestHeader;
import http.RequestLine;
import http.RequestMethod;

public class IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static final String BLANK = " ";
    public static final int URL_INDEX = 1;

    /**
     * @param BufferedReader는 Request Body를 시작하는 시점이어야
     * @param contentLength는  Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static RequestLine ofRequestLine(String requestLine) {
        String[] tokens = requestLine.split(BLANK);
        return new RequestLine(RequestMethod.valueOf(tokens[0]), tokens[1], tokens[2]);
    }

    public static RequestHeader ofRequestHeader(BufferedReader br) throws IOException {
        RequestHeader requestHeader = new RequestHeader();
        String line = " ";
        while (!"".equals(line)) {
            line = br.readLine();
            if ("".equals(line) || line == null) {
                break;
            }
            String[] token = line.split(": ");
            requestHeader.put(token[0], token[1]);
        }

        return requestHeader;
    }

    public static RequestBody ofRequestBody(BufferedReader br) throws IOException {
        RequestBody requestBody = new RequestBody();
        String line = br.readLine();
        if (line != null) {
            String[] tokens = line.split("&");
            for (String token : tokens) {
                String[] value = token.split("=");

                requestBody.put(value[0], value[1]);
            }
        }
        return requestBody;
    }

    public static void printRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        if (br == null) {
            return;
        }
        String result = "\n";
        String line = " ";
        while (!"".equals(line)) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            result += line + "\n";
        }
        logger.debug(result);
    }
}
