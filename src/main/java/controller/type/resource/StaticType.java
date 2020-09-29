package controller.type.resource;

import java.util.Arrays;

public enum StaticType {

    CSS("css"),
    JS("js"),
    WOFF("woff"),
    WOFF2("woff2"),
    NONE("none");

    private final String staticType;

    StaticType(final String staticType) {
        this.staticType = staticType;
    }

    public static StaticType find(final String staticType) {
        return Arrays.stream(StaticType.values())
                .filter(type -> type.staticType.equals(staticType))
                .findFirst()
                .orElse(NONE);
    }
}
