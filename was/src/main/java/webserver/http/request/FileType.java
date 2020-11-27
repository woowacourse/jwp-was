package webserver.http.request;

public class FileType {
    private static final String TEMPLATE = "./templates";
    private static final String STATIC = "./static";

    private final String classPath;
    private final String contentType;

    public FileType(String classPath, String contentType) {
        this.classPath = classPath;
        this.contentType = contentType;
    }

    public static FileType of(String uri) {
        if (TemplateFile.isTemplate(uri)) {
            TemplateFile templateFile = TemplateFile.of(uri);
            return new FileType(TEMPLATE, templateFile.getContentType());
        }
        if (StaticFile.isStatic(uri)) {
            StaticFile staticFile = StaticFile.of(uri);
            return new FileType(STATIC, staticFile.getContentType());
        }
        throw new IllegalArgumentException("지원하지 않는 형태의 파일입니다.");
    }

    public String getContentType() {
        return contentType;
    }

    public String getClassPath() {
        return classPath;
    }
}
