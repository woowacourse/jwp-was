package view;

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
        return responseBody == null || responseBody.length == 0;
    }

    @Override
    public boolean isNotEmpty() {
        return !isEmpty();
    }
}
