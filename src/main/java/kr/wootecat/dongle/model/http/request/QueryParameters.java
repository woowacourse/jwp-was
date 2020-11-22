package kr.wootecat.dongle.model.http.request;

import static java.lang.String.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.wootecat.dongle.model.http.exception.IllegalRequestDataFormatException;

class QueryParameters {

    private static final Pattern QUERY_PARAMETER_PATTERN = Pattern.compile("[\\w-]+=[^?=&\\s]*(&[\\w-]+=[^?=&\\s]*)*");

    private static final String EACH_PAIR_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String EMPTY_STRING = "";

    private static final int PAIR_LENGTH = 2;

    private static final String ILLEGAL_REQUEST_FORMAT_EXCEPTION_MESSAGE_FORMAT = "유효하지 않는 요청 데이터 형식힙니다.: %s";

    private final Map<String, String> queryParameters;

    public QueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public static QueryParameters from(String rawData) {
        HashMap<String, String> parameters = new HashMap<>();
        if (rawData.isEmpty()) {
            return new QueryParameters(new HashMap<>());
        }
        validateQueryParam(rawData);
        String[] eachPairs = rawData.split(EACH_PAIR_DELIMITER);
        for (String eachPair : eachPairs) {
            String[] keyValuePair = eachPair.split(KEY_VALUE_DELIMITER);
            String name = keyValuePair[0];
            String value = getValueFrom(keyValuePair);
            parameters.put(name, value);
        }

        return new QueryParameters(parameters);
    }

    public static QueryParameters empty() {
        return new QueryParameters(new HashMap<>());
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

    public String get(String key) {
        return queryParameters.get(key);
    }

    public boolean isEmpty() {
        return queryParameters.isEmpty();
    }
}
