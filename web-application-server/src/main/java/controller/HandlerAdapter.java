package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import type.resource.FileType;
import type.resource.ResourceType;

public class HandlerAdapter {

    private static final String PERIOD = "\\.";

    private final Controller templateController = new TemplateController();
    private final Controller staticController = new StaticController();
    private final HandlerMapping handlerMapping;

    private HandlerAdapter(final HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public static HandlerAdapter of(final HandlerMapping handlerMapping) {
        return new HandlerAdapter(handlerMapping);
    }

    public Controller find(final String url) {
        final Controller controller = handlerMapping.get(url);

        if (Objects.isNull(controller)) {
            return findResourceController(url);
        }
        return controller;
    }

    private Controller findResourceController(final String url) {
        final String[] splittedUrl = url.split(PERIOD);
        final FileType fileType = FileType.find(splittedUrl[splittedUrl.length - 1]);

        if (ResourceType.TEMPLATE == fileType.getResourceType()) {
            return templateController;
        }
        return staticController;
    }
}
