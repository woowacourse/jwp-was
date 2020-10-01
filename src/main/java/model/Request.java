package model;

import utils.StringUtils;

public class Request {

    private Method method;
    private String location;
    private ContentType contentType;

    private Request(String method, String location, String contentType) {
        this.method = Method.of(method);
        this.location = location;
        this.contentType = ContentType.of(contentType);
    }

    public static Request of(String line) {
        String method = StringUtils.extractRequestMethod(line);
        String location = StringUtils.extractRequestLocation(line);
        String contentType = StringUtils.extractExtension(line);

        return new Request(method, location, contentType);
    }

    public boolean isSameMethod(Method method) {
        return this.method.equals(method);
    }

    public boolean isNeedBody(){
        return method.isNeedBody();
    }

    public String getLocation() {
        return location;
    }

    public ContentType getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "Request{" +
            "method=" + method +
            ", location='" + location + '\'' +
            ", contentType=" + contentType +
            '}';
    }
}
