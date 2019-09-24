package utils;

public class ExtensionParser {
    public static String parse(String path) {
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        String[] splicedFileName = fileName.split("\\.");
        return splicedFileName[splicedFileName.length - 1];
    }
}
