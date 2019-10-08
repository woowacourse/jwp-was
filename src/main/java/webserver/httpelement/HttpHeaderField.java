package webserver.httpelement;

import utils.StringUtils;

public interface HttpHeaderField {
    static String getName(Class<? extends HttpHeaderField> headerFieldClass) {
        return StringUtils.pascalToKebobCase(headerFieldClass.getSimpleName().split("Http")[1]);
    }

    default String fieldName() {
        return getName(getClass());
    }
}