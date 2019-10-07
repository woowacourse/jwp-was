package view;

import http.response.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class ViewResolver {
    private static List<ViewProcessor> viewProcessors = new ArrayList<>();

    static {
        viewProcessors.add(new TemplateViewProcessor());
        viewProcessors.add(new RedirectViewProcessor());
    }

    public static byte[] render(ModelAndView modelAndView, HttpResponse response) {
        String viewName = modelAndView.getView();

        ViewProcessor viewProcessor = viewProcessors.stream()
                .filter(pr -> pr.isSupported(viewName))
                .findFirst()
                .orElseThrow(NotSupportedViewProcessorException::new);

        return viewProcessor.render(modelAndView, response);
    }
}
