package webserver.utils;

public class ResourcePathUtils {
	public static final String FILE_EXTENSION_SEPARATOR = ".";

	public static String getResourcePath(String path) {
		return FileExtension.getFilePath(getFileExtension(path)) + path;
	}

	private static String getFileExtension(String path) {
		return path.substring(path.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1);
	}
}
