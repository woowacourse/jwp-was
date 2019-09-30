package view;

public class View {
    private String viewPath;
    private byte[] viewData;

    public View(String view) {
        this.viewPath = view;
    }

    public View(byte[] viewData) {
        this.viewData = viewData;
    }

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public byte[] getByteView() {
        return viewData;
    }

    public void setByteView(byte[] viewData) {
        this.viewData = viewData;
    }
}
