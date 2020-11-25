package type.resource;

import java.util.Arrays;

import exception.NotFoundFileTypeException;

public enum FileType {

    HTML("html", ResourceType.TEMPLATE),
    ICO("ico", ResourceType.TEMPLATE),
    CSS("css", ResourceType.STATIC),
    JS("js", ResourceType.STATIC),
    PNG("png", ResourceType.STATIC),
    WOFF("woff", ResourceType.STATIC),
    WOFF2("woff2", ResourceType.STATIC);

    private final String type;
    private final ResourceType resourceType;

    FileType(final String type, final ResourceType resourceType) {
        this.type = type;
        this.resourceType = resourceType;
    }

    public static FileType find(final String fileType) {
        return Arrays.stream(FileType.values())
                .filter(type -> type.type.equals(fileType))
                .findFirst()
                .orElseThrow(() -> new NotFoundFileTypeException("파일 형식을 찾을 수 없습니다."));
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
}
