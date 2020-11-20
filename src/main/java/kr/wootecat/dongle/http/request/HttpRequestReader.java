package kr.wootecat.dongle.http.request;

import static com.google.common.net.HttpHeaders.*;
import static java.lang.String.*;
import static kr.wootecat.dongle.http.CookieParser.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.wootecat.dongle.http.Cookie;
import kr.wootecat.dongle.http.HttpMethod;
import kr.wootecat.dongle.http.exception.IllegalRequestDataFormatException;
import utils.IOUtils;

public class HttpRequestReader {

    private static final Pattern QUERY_PARAMETER_PATTERN = Pattern.compile("[\\w-]+=[^?=&\\s]*(&[\\w-]+=[^?=&\\s]*)*");
    private static final Pattern HEADER_PATTERN = Pattern.compile("[\\w-]+: [-,;:+?*/=.()\\s\\w]+");

    private static final String HTTP_SPACE_CHARACTER = " ";
    private static final String QUERY_PARAMETER_DELIMITER = "\\?";
    private static final String HEADER_KEY_VALUE_DELIMITER = ": ";
    private static final String UTF_8_ENCODING_TYPE = "UTF-8";
    private static final String EACH_PAIR_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String EMPTY_STRING = "";

    private static final int COUNT_OF_REQUEST_LINE_DATA = 3;
    private static final int PAIR_LENGTH = 2;

    private static final String ILLEGAL_REQUEST_FORMAT_EXCEPTION_MESSAGE_FORMAT = "유효하지 않는 요청 데이터 형식힙니다.: %s";
    private static final String ILLEGAL_REQUEST_LINE_FORMAT_EXCEPTION_MESSAGE = "올바른 Request line 형식이 아닙니다.";

    private HttpRequestReader() {
    }

    public static HttpRequest parse(BufferedReader br) throws IOException {
        String httpRequestLine = br.readLine();
        String[] requestLines = httpRequestLine.split(HTTP_SPACE_CHARACTER);
        if (requestLines.length != COUNT_OF_REQUEST_LINE_DATA) {
            throw new IllegalRequestDataFormatException(ILLEGAL_REQUEST_LINE_FORMAT_EXCEPTION_MESSAGE);
        }

        HttpMethod requestMethod = HttpMethod.valueOf(requestLines[0]);
        String uri = requestLines[1];
        String[] uriQueryPair = uri.split(QUERY_PARAMETER_DELIMITER);
        String path = uriQueryPair[0];
        String protocolVersion = requestLines[2];

        HttpRequestLine requestLine = new HttpRequestLine(requestMethod, path, protocolVersion);
        HttpRequestHeaders requestHeader = parseRequestHeader(br);
        HttpRequestParameters requestParameters = parseRequestParam(br, requestMethod, uriQueryPair, requestHeader);

        return new HttpRequest(requestLine, requestHeader, requestParameters);
    }

    private static HttpRequestHeaders parseRequestHeader(BufferedReader br) throws IOException {
        Map<String, String> headers = new HashMap<>();
        List<Cookie> cookies = new ArrayList<>();

        String singleLine = br.readLine();
        while (singleLine != null && !singleLine.isEmpty()) {
            validateHeaderLine(singleLine);
            String[] headerPair = singleLine.split(HEADER_KEY_VALUE_DELIMITER);
            String name = headerPair[0];
            String value = getValueFrom(headerPair);
            if (COOKIE.equals(name)) {
                cookies = toCookie(value);
            } else {
                headers.put(name, value);
            }
            singleLine = br.readLine();
        }
        return new HttpRequestHeaders(headers, cookies);
    }

    private static HttpRequestParameters parseRequestParam(BufferedReader br, HttpMethod requestMethod,
            String[] uriQueryPair, HttpRequestHeaders requestHeader) throws IOException {
        Map<String, String> parameters = new HashMap<>();

        if (uriQueryPair.length == PAIR_LENGTH && requestMethod.isGet()) {
            String queryString = uriQueryPair[1];
            inputToRequestParameter(parameters, queryString);
        } else if (requestMethod.isPost()) {
            String requestBodyData = IOUtils.readData(br, Integer.parseInt(requestHeader.get(CONTENT_LENGTH)));
            inputToRequestParameter(parameters, requestBodyData);
        }
        return new HttpRequestParameters(parameters);

    }

    private static void inputToRequestParameter(Map<String, String> params, String data) throws
            UnsupportedEncodingException {
        if (data.isEmpty()) {
            return;
        }

        String decodedData = URLDecoder.decode(data, UTF_8_ENCODING_TYPE);
        validateQueryParam(decodedData);
        String[] eachPairs = decodedData.split(EACH_PAIR_DELIMITER);
        for (String eachPair : eachPairs) {
            String[] keyValuePair = eachPair.split(KEY_VALUE_DELIMITER);
            String name = keyValuePair[0];
            String value = getValueFrom(keyValuePair);
            params.put(name, value);
        }
    }

    private static String getValueFrom(String[] pair) {
        return pair.length == PAIR_LENGTH ? pair[1] : EMPTY_STRING;
    }

    private static void validateQueryParam(String queryParam) {
        Matcher queryParameterMatcher = QUERY_PARAMETER_PATTERN.matcher(queryParam);
        if (!queryParameterMatcher.matches()) {
            throw new IllegalRequestDataFormatException(format(
                    ILLEGAL_REQUEST_FORMAT_EXCEPTION_MESSAGE_FORMAT, queryParam));
        }
    }

    private static void validateHeaderLine(String header) {
        Matcher headerMatcher = HEADER_PATTERN.matcher(header);
        if (!headerMatcher.matches()) {
            throw new IllegalRequestDataFormatException(
                    format(ILLEGAL_REQUEST_FORMAT_EXCEPTION_MESSAGE_FORMAT, header));
        }
    }
}
