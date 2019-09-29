package view;

import http.HttpMimeType;

public interface View {
    byte[] render();
    HttpMimeType getMimeType();
}
