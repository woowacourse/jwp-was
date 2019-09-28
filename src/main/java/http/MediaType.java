package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum MediaType {
	HTML("html", "templates", "text/html"),
	CSS("css", "static", "text/css"),
	JS("js", "static", "application/js"),
	ICO("ico", "static", "image/x-icon"),
	NONE("", "", ""),
	WOFF("woff", "static", "application/x-font-woff"),
	TTF("ttf", "static", "application/x-font-ttf");

	private String extension;
	private String path;
	private String contentType;

	MediaType(String extension, String path, String contentType) {
		this.extension = extension;
		this.path = path;
		this.contentType = contentType;
	}

	public static MediaType of(String mediaType) {
		return Arrays.stream(values())
				.filter(value -> value.extension.equals(mediaType))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new)
				;
	}

	public static String getFullPath(String uri) {
		Logger logger = LoggerFactory.getLogger(MediaType.class);

		logger.debug("uri: {}", uri);

		return Arrays.stream(values())
				.filter(value -> uri.contains(value.extension))
				.map(value -> "./" + value.path + uri)
				.findFirst()
				.orElseThrow(IllegalArgumentException::new)
				;
	}

	public static String getContentType(String uri) {
		return Arrays.stream(values())
				.filter(value -> uri.contains(value.extension))
				.map(value -> value.contentType)
				.findFirst()
				.orElseThrow(IllegalArgumentException::new)
				;
	}

	public static boolean isContain(String extension) {
		if (!extension.contains(".")) {
			return false;
		}

		return Arrays.stream(values())
				.map(value -> extension.contains(value.extension))
				.filter(value -> value)
				.findFirst()
				.orElse(false)
				;
	}
}
