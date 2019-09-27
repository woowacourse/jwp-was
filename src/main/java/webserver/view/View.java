package webserver.view;

public class View {
    private final byte[] body;

    public View(byte[] body) {
        this.body = body;
    }

    public View() {
        this.body = new byte[0];
    }

    public byte[] getBody() {
        return body;
    }

    public boolean isViewExists() {
        return body == null || body.length == 0;
    }
}
