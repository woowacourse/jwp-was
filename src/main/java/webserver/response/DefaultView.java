package webserver.response;

import java.io.IOException;

import utils.FileIoUtils;

public class DefaultView implements View {
    private final byte[] view;
    private final String viewName;

    DefaultView(String viewName) throws IOException {
        this.view = FileIoUtils.loadFileFromClasspath(viewName);
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
        return false;
    }

    @Override
    public String getViewName() {
        return viewName;
    }
}
