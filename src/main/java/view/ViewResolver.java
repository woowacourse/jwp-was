package view;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ViewResolver {
    public static byte[] resolve(String viewName) throws IOException, URISyntaxException {
        String path = "./templates/" + viewName;
        if (FileIoUtils.isExistFile(path)) {
            return FileIoUtils.loadFileFromClasspath(path);
        }
        return FileIoUtils.loadFileFromClasspath("./static/" + viewName);
    }
}