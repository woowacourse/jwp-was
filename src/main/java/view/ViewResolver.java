package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.exception.NotSupportedViewException;
import webserver.RequestHandler;
import webserver.http.ModelAndView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final ViewProcessor[] VIEW_RESOLVERS = {
            new TemplateEngineProcessor(),
            new RedirectProcessor(),
            new ResourceFileProcessor(),
    };

    public static void resolve(ModelAndView modelAndView) throws IOException, URISyntaxException {
        Arrays.stream(VIEW_RESOLVERS)
                .filter(viewProcessor -> viewProcessor.isMapping(modelAndView))
                .findFirst()
                .orElseThrow(NotSupportedViewException::new)
                .resolve(modelAndView)
        ;
    }
}
