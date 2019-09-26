package view;

public interface View {
    byte[] getBody();

    int getContentLength();

    boolean isEmpty();
}
