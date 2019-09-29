package view;

public class SimpleView implements View {
    private String view;

    public SimpleView(String view) {
        this.view = view;
    }

    @Override
    public byte[] render() {
        return view == null ? null : view.getBytes();
    }
}
