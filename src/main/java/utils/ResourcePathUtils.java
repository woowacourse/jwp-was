package utils;

public class ResourcePathUtils {
	public static String getResourcePath(String path) {
		return FileExtension.getFilePath(path.substring(path.lastIndexOf(".") + 1)) + path;
	}
}
