package webserver;

public class View {
    private final byte[] view;

    public View(final byte[] view) {
        this.view = view;
    }

    public byte[] getView() {
        return view;
    }

    public int getLength(){
        return view.length;
    }

    public boolean isNotEmpty() {
        return view.length != 0;
    }
}
