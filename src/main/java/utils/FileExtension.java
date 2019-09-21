package utils;

import java.util.Arrays;

public enum FileExtension {
	HTML("html", "./templates"),
	ETC("etc", "./static");

	private String fileExtension;
	private String filePath;

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
}
