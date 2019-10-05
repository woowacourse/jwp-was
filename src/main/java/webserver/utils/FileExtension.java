package webserver.utils;

import java.util.Arrays;

public enum FileExtension {
	HTML("html", "./templates"),
	ETC("etc", "./static");

	private final String fileExtension;
	private final String filePath;

	FileExtension(String fileExtension, String filePath) {
		this.fileExtension = fileExtension;
		this.filePath = filePath;
	}

	public static String getFilePath(String fileExtension) {
		return Arrays.stream(FileExtension.values())
				.filter(extension
						-> extension.fileExtension.equals(fileExtension))
				.findFirst().orElse(ETC)
				.filePath;
	}

	public String getFileExtension() {
		return fileExtension;
	}
}
