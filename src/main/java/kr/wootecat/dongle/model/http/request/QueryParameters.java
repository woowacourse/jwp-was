package kr.wootecat.dongle.model.http.request;

import static java.lang.String.*;
import static kr.wootecat.dongle.utils.SplitUtils.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.wootecat.dongle.model.http.exception.IllegalRequestDataFormatException;

class QueryParameters {

    private static final Pattern QUERY_PARAMETER_PATTERN = Pattern.compile("[\\w-]+=[^?=&\\s]*(&[\\w-]+=[^?=&\\s]*)*");

    private static final String EACH_PAIR_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private static final String ILLEGAL_REQUEST_FORMAT_EXCEPTION_MESSAGE_FORMAT = "유효하지 않는 요청 데이터 형식힙니다.: %s";

    private final Map<String, String> queryParameters;

    public QueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public static QueryParameters empty() {
        return new QueryParameters(new HashMap<>());
    }

    public static QueryParameters from(String rawData) {
        if (rawData.isEmpty()) {
            return empty();
        }
        validateQueryParam(rawData);
        Map<String, String> parameters = splitAndThenCollect(rawData, EACH_PAIR_DELIMITER, KEY_VALUE_DELIMITER);
        return new QueryParameters(parameters);
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
