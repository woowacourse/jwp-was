package view;

import http.HttpMimeType;

public class SimpleView implements View {
    private String view;

    public SimpleView(String view) {
        this.view = view;
    }

    @Override
    public byte[] render() {
        return view == null ? null : view.getBytes();
    }

    @Override
    public HttpMimeType getMimeType() {
        return HttpMimeType.HTML;
    }
}
