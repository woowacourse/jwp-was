package http;

import utils.FileIoUtils;

public class ViewResolver implements Resolver {
    private static final String CURRENT_PATH = "./templates";

    @Override
    public Object resolve(ModelAndView modelAndView) {
        return FileIoUtils.loadFileFromClasspath(CURRENT_PATH + modelAndView.getViewLocation());
    }
}
