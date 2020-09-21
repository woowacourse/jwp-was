package webserver.response;

import java.io.IOException;

import exception.ViewNotFoundException;
import utils.FileIoUtils;

public class View {
    private final byte[] view;

    public View(final byte[] view) {
        this.view = view;
    }

    public static View of(final String viewName) {
        try {
            return new View(FileIoUtils.loadFileFromClasspath(viewName));
        } catch (IOException e) {
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
}
