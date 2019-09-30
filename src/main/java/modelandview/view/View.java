package modelandview.view;

import modelandview.model.Model;

import java.io.IOException;

public interface View {
    byte[] render(String location, Model model) throws IOException;
}
