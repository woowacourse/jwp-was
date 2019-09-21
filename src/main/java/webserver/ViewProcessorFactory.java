package webserver;

import webserver.viewProcessor.*;

import java.util.ArrayList;
import java.util.List;

public class ViewProcessorFactory {
    private static List<ViewProcessor> viewProcessors = new ArrayList<>();

    //TODO RedirectViewProcessor가 처음에 있지 않고 순서가 바뀌면 리다이렉트 시 HTML 파일일 경우는 HtmlViewProcessor가 추출된다.
    static {
        viewProcessors.add(new RedirectViewProcessor());
        viewProcessors.add(new CssViewProcessor());
        viewProcessors.add(new HtmlViewProcessor());
        viewProcessors.add(new JsViewProcessor());
        viewProcessors.add(new PlainViewProcessor());
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
