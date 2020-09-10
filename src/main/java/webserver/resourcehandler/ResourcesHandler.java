package webserver.resourcehandler;

import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;

public class ResourcesHandler {

    public byte[] convertUriToResource(String uri) throws IOException, URISyntaxException {
        String filePath = "./templates" + uri;

        if ("./templates/".equals(filePath)) {
            filePath = "./templates/index.html";
        }
        return FileIoUtils.loadFileFromClasspath(filePath);
    }
}
