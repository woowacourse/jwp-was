package webserver;

import webserver.viewProcessor.ViewProcessor;
import webserver.viewProcessor.*;

import java.util.ArrayList;
import java.util.List;

public class ViewProcessorFactory {
    private static List<ViewProcessor> viewProcessors = new ArrayList<>();

    static {
        viewProcessors.add(new CssViewProcessor());
        viewProcessors.add(new HtmlViewProcessor());
        viewProcessors.add(new JsViewProcessor());
        viewProcessors.add(new PlainViewProcessor());
        viewProcessors.add(new RedirectViewProcessor());
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
