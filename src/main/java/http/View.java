package http;

public interface View {
    String render(ModelMap modelMap);

    String getViewName();
}

