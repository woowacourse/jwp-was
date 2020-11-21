package webserver.response;

import java.io.IOException;

import exception.InvalidRedirectViewNameException;
import utils.FileIoUtils;

public class RedirectView implements View {
    static final String REDIRECT_PREFIX = "redirect:/";

    private final byte[] view;
    private final String viewName;

    RedirectView(String viewName) throws IOException {
        if (!viewName.startsWith(REDIRECT_PREFIX)) {
            throw new InvalidRedirectViewNameException(viewName);
        }
        this.view = FileIoUtils.loadFileFromClasspath(viewName.substring(10));
        this.viewName = viewName;
    }

    @Override
    public byte[] getView() {
        return view;
    }

    @Override
    public int getLength() {
        return view.length;
    }

    @Override
    public boolean isNotEmpty() {
        return view.length != 0;
    }

    @Override
    public boolean isRedirect() {
        return true;
    }

    @Override
    public String getViewName() {
        return viewName;
    }
}
