package webserver;

import java.util.Arrays;

public enum ContentTypeMatcher {
	HTML(".html", "./templates"),
	JS(".js", "./static"),
	CSS(".css", "./static");

	private final String fileType;
	private final String filePath;

	ContentTypeMatcher(String fileType, String filePath) {
		this.fileType = fileType;
		this.filePath = filePath;
	}

	public static ContentTypeMatcher findContentType(String path) {
		return Arrays.stream(values())
			.filter(type -> path.endsWith(type.fileType))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 파일 타입입니다."));
	}

	public String parseFilePath(String path) {
		return this.filePath + path;
	}
}
