package http.request;

import java.util.Arrays;

public enum TemplateType {

    HTML("html"),
    ICO("ico"),
    NONE("none");

    private final String templateType;

    TemplateType(final String templateType) {
        this.templateType = templateType;
    }

    public static TemplateType find(final String templateType) {
        return Arrays.stream(TemplateType.values())
                .filter(type -> type.templateType.equals(templateType))
                .findFirst()
                .orElse(NONE);
    }
}
