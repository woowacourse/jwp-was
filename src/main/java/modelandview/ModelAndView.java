package modelandview;

import java.io.IOException;

public interface ModelAndView {
    public byte[] render(String location) throws IOException;
    public void putData(String name, Object o);
    public String getPreFix();
    public String getSufFix();
}
