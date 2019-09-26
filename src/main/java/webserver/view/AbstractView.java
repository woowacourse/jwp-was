package webserver.view;

public abstract class AbstractView implements View {

    protected byte[] responseBody;

    @Override
    public byte[] getBody() {
        return responseBody;
    }

    @Override
    public int getContentLength() {
        return responseBody.length;
    }

    @Override
    public boolean isEmpty() {
        return responseBody.length == 0;
    }
}
