package webserver.view;


public class EmptyView extends AbstractView {

    private EmptyView() {
    }

    @Override
    public byte[] getBody() {
        return null;
    }

    @Override
    public int getContentLength() {
        return 0;
    }

    public static EmptyView getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final EmptyView INSTANCE = new EmptyView();
    }
}
