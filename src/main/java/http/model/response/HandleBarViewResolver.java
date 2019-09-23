package http.model.response;

import java.io.DataOutputStream;

public class HandleBarViewResolver implements ViewResolver {
    private static final String FORWARD = "forward:";
    private String viewName;
    private boolean useTemplete = false;

    public HandleBarViewResolver(String viewName) {
        this.viewName = checkViewName(viewName);
    }

    private String checkViewName(String viewName) {
        if (viewName.startsWith(FORWARD)) {
            this.useTemplete = true;
            return viewName.substring(viewName.indexOf(FORWARD) + 1);
        }
        return viewName;
    }

    @Override
    public void render(DataOutputStream dataOutputStream) {

    }
}
