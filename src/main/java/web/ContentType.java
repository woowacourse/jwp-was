package web;

import java.util.Arrays;

public enum ContentType {
	HTML("html", "text/html"),
	CSS("css", "text/css"),
	JS("js", "application/javascript"),
	ICO("ico", "image/vnd.microsoft.icon"),
	WOFF("woff", "text/woff"),
	PNG("png", "image/png"),
	JPEG("jpeg", "image/jpeg"),
	SVG("svg", "image/svg_xml");

	private final String fileType;
	private final String contentType;

	ContentType(String fileType, String contentType) {
		this.fileType = fileType;
		this.contentType = contentType;
	}

	public static ContentType of(String path) {
		return Arrays.stream(ContentType.values())
			.filter(type -> path.endsWith(type.getFileType()))
			.findAny()
			.orElseThrow(RuntimeException::new);
	}

	public String getFileType() {
		return fileType;
	}

	public String getContentType() {
		return contentType;
	}
}
