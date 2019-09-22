package webserver;

import webserver.viewProcessor.*;

import java.util.ArrayList;
import java.util.List;

public class ViewProcessorFactory {
    private List<ViewProcessor> viewProcessors = new ArrayList<>();

    private ViewProcessorFactory() {
        initViewProcessorFactory();
    }

    public static ViewProcessorFactory getInstance() {
        return ViewProcessorFactory.LazyHolder.INSTANCE;
    }

    private void initViewProcessorFactory() {
        viewProcessors.add(new RedirectViewProcessor());
        viewProcessors.add(new CssViewProcessor());
        viewProcessors.add(new HtmlViewProcessor());
        viewProcessors.add(new JsViewProcessor());
        viewProcessors.add(new PlainViewProcessor());
    }

    private static class LazyHolder {
        private static final ViewProcessorFactory INSTANCE = new ViewProcessorFactory();
    }

    public ViewProcessor getViewProcessor(String name) {
        for (ViewProcessor viewProcessor : viewProcessors) {
            if (viewProcessor.isSupported(name)) {
                return viewProcessor;
            }
        }

        throw new IllegalArgumentException("지원 하지 않는 파일 타입 입니다.");
    }
}
