package model;

import static utils.Strings.*;

import java.util.Arrays;
import java.util.function.Predicate;

public enum RequestURIType {
    NO_PARAMS(uri -> !uri.contains(ParamsFactory.QUESTION_MARK), new NoParamsFactory()),
    HAVE_PARAMS(uri -> uri.contains(ParamsFactory.QUESTION_MARK), new ParamsFactory());

    private final Predicate<String> judge;
    private final RequestURIFactory factory;

    RequestURIType(Predicate<String> judge, RequestURIFactory factory) {
        this.judge = judge;
        this.factory = factory;
    }

    public static RequestURIType of(String uri) {
        return Arrays.stream(values())
                .filter(value -> value.judge.test(uri))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("지원하지 않는 uri 입니다. uri : " + uri));
    }

    public RequestURIFactory getFactory() {
        return factory;
    }

    static class NoParamsFactory implements RequestURIFactory {
        @Override
        public RequestURI create(String uri) {
            return new RequestURI(uri, HttpParams.of(EMPTY));
        }
    }

    static class ParamsFactory implements RequestURIFactory {
        private static final String QUESTION_MARK = "?";
        private static final String QUERY_STRING_DELIMITER = "\\" + QUESTION_MARK;
        private static final int URI = 0;
        private static final int QUERY_PARAMS = 1;

        @Override
        public RequestURI create(String uri) {
            String[] uriAndQueryParams = uri.split(QUERY_STRING_DELIMITER);
            return new RequestURI(uriAndQueryParams[URI],
                    HttpParams.of(uriAndQueryParams[QUERY_PARAMS]));
        }
    }
}
