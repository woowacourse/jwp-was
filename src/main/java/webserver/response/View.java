package webserver.response;

import java.io.IOException;

import exception.ViewNotFoundException;
import utils.FileIoUtils;

public class View {
    private final byte[] view;
    private final String viewName;
    private final boolean isRedirect;

    public View(byte[] view, String viewName) {
        this(view, viewName, false);
    }

    private View(byte[] view, String viewName, boolean isRedirect) {
        this.view = view;
        this.viewName = viewName;
        this.isRedirect = isRedirect;
    }

    public static View of(String viewName) {
        try {
            if (viewName.startsWith("redirect:/")) {
                viewName = viewName.substring(10);
                return new View(FileIoUtils.loadFileFromClasspath(viewName), viewName, true);
            }
            return new View(FileIoUtils.loadFileFromClasspath(viewName), viewName);
        } catch (IOException | ViewNotFoundException exception) {
            throw new ViewNotFoundException(viewName);
        }
    }

    public byte[] getView() {
        return view;
    }

    public int getLength() {
        return view.length;
    }

    public boolean isNotEmpty() {
        return view.length != 0;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public String getViewName() {
        return viewName;
    }
}
