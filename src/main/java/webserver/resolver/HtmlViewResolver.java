package webserver.resolver;

import utils.FileIoUtils;
import webserver.view.ForwardView;
import webserver.view.RedirectView;
import webserver.view.View;

public class HtmlViewResolver implements Resolver {
    private final String REDIRECT_PREFIX = "redirect:";
    private final String POSTFIX = ".html";
    private final String BLANK = "";

    @Override
    public View createView(String viewName) {
        if (viewName.startsWith(REDIRECT_PREFIX)) {

            return new RedirectView(viewName.replace(REDIRECT_PREFIX,BLANK));
        }
        return new ForwardView(FileIoUtils.generateHtmlFilePath(viewName + POSTFIX));
    }
}
