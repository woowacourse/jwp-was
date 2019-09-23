package webserver.http;

import utils.StringUtils;

public interface HttpHeaderField {
    static String getName(Class headerFieldClass) {
        return StringUtils.pascalToKebobCase(headerFieldClass.getSimpleName().split("Http")[1]);
    }

    default String fieldName() {
        return getName(getClass());
    }
}