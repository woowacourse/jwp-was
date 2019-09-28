package webserver.resolver;

import utils.FileIoUtils;
import webserver.view.FileView;
import webserver.view.View;

import java.io.IOException;
import java.net.URISyntaxException;

public class FileResolver implements Resolver {
    @Override
    public View createView(String viewName){
        if (viewName.contains(".html")) {
            return new FileView(FileIoUtils.generateHtmlFilePath(viewName));
        }
        return new FileView(FileIoUtils.generateStaticFilePath(viewName));
    }
}
