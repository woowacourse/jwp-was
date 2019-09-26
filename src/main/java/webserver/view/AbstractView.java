package webserver.view;

public abstract class AbstractView implements View {

    protected String path;
    protected byte[] responseBody;

    protected abstract void setPath(final String path);

    protected abstract void setResponseBody();

    @Override
    public byte[] getBody() {
        return responseBody;
    }

    @Override
    public int getContentLength() {
        return responseBody.length;
    }
}
