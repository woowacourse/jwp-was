package webserver.resolver;

import utils.FileIoUtils;
import webserver.view.ForwardView;
import webserver.view.View;

public class HtmlViewResolver implements Resolver {
    final String PREFIX = "/";
    final String POSTFIX = ".html";

    @Override
    public View createView(String viewName) {
        return new ForwardView(FileIoUtils.generateHtmlFilePath(PREFIX + viewName + POSTFIX));
    }
}
