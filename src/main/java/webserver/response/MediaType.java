package webserver.response;

import java.util.Arrays;

// TODO: 2019-09-19 개선 방안 찾아보기
public enum MediaType {

    ALL_VALUE("*/*"),
    //    APPLICATION_ATOM_XML_VALUE("application/atom+xml"),
//    APPLICATION_FORM_URLENCODED_VALUE("application/x-www-form-urlencoded"),
//    APPLICATION_JSON_VALUE("application/json"),
//    APPLICATION_JSON_UTF8_VALUE("application/json;charset=UTF-8"),
//    APPLICATION_OCTET_STREAM_VALUE("application/octet-stream"),
//    APPLICATION_PDF_VALUE("application/pdf"),
//    APPLICATION_PROBLEM_JSON_VALUE("application/problem+json"),
//    APPLICATION_PROBLEM_JSON_UTF8_VALUE("application/problem+json;charset=UTF-8"),
//    APPLICATION_PROBLEM_XML_VALUE("application/problem+xml"),
//    APPLICATION_RSS_XML_VALUE("application/rss+xml"),
//    APPLICATION_STREAM_JSON_VALUE("application/stream+json"),
//    APPLICATION_XHTML_XML_VALUE("application/xhtml+xml"),
//    APPLICATION_XML_VALUE("application/xml"),
    IMAGE_GIF_VALUE("image/gif"),
    IMAGE_JPEG_VALUE("image/jpeg"),
    IMAGE_PNG_VALUE("image/png"),
    //    MULTIPART_FORM_DATA_VALUE("multipart/form-data"),
//    TEXT_EVENT_STREAM_VALUE("text/event-stream"),
    TEXT_HTML_VALUE("text/html"),
    //    TEXT_MARKDOWN_VALUE("text/markdown"),
//    TEXT_PLAIN_VALUE("text/plain"),
    TEXT_XML_VALUE("text/xml"),
    TEXT_CSS_VALUE("text/css"),
    TEXT_JAVASCRIPT_VALUE("text/js"),
    FONT_WOFF("font/woff"),
    FONT_TTF("font/ttf"),
    IMAGE_X_ICON_VALUE("image/x-icon");

    private String mediaType;

    MediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public static MediaType of(String mediaType) {
        return Arrays.stream(MediaType.values())
                .filter(m -> m.contains(mediaType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 MediaType"));
    }

    private boolean contains(String mediaType) {
        return this.mediaType.contains(mediaType);
    }

    public String getMediaType() {
        return mediaType;
    }
}
