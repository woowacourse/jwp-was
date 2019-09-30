package utils.modelandview;

import utils.modelandview.model.HandleBarModel;
import utils.modelandview.model.Model;
import utils.modelandview.view.HandleBarView;
import utils.modelandview.view.View;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HandleBarModelAndView implements ModelAndView {
    public static final String DEFAULT_PRE_FIX = "/templates";
    public static final String DEFAULT_SUF_FIX = ".html";

    private Model model;
    private View view;

    public HandleBarModelAndView() {
        model = new HandleBarModel();
        view = new HandleBarView(DEFAULT_PRE_FIX, DEFAULT_SUF_FIX, StandardCharsets.UTF_8);
    }

    @Override
    public void putData(String name, Object o) {
        model.putData(name, o);
    }

    @Override
    public byte[] render(String location) throws IOException {
        return view.render(location, model);
    }

    @Override
    public String getPreFix() {
        return DEFAULT_PRE_FIX;
    }

    @Override
    public String getSufFix() {
        return DEFAULT_SUF_FIX;
    }
}