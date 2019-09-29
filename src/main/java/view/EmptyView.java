package view;

import http.HttpMimeType;

public class EmptyView implements View {
    @Override
    public byte[] render() {
        return null;
    }

    @Override
    public HttpMimeType getMimeType() {
        return null;
    }
}
