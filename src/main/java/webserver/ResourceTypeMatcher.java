package webserver;

import java.util.Arrays;

public enum ResourceTypeMatcher {
	HTML(".html", "./templates", "text/html;charset=UTF-8"),
	JS(".js", "./static", "application/javascript;charset=UTF-8"),
	CSS(".css", "./static", "text/css;charset=UTF-8"),
	WOFF(".woff", "./static", "font/woff;charset=UTF-8");

	private final String fileType;
	private final String filePath;
	private final String contentType;

	ResourceTypeMatcher(String fileType, String filePath, String contentType) {
		this.fileType = fileType;
		this.filePath = filePath;
		this.contentType = contentType;
	}

	public static ResourceTypeMatcher findContentType(String path) {
		return Arrays.stream(values())
			.filter(type -> path.endsWith(type.fileType))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 파일 타입입니다."));
	}

	public String parseFilePath(String path) {
		return this.filePath + path;
	}

	public static boolean isContainType(String path) {
		return Arrays.stream(values())
			.anyMatch(type -> path.endsWith(type.fileType));
	}

	public String getContentType() {
		return contentType;
	}
}
