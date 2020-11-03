package webserver;

import webserver.request.MethodType;
import webserver.request.ServletRequest;
import webserver.response.ModelAndView;
import webserver.response.ServletResponse;

public class DefaultStaticResourceHandler implements StaticResourceHandler{
    @Override
    public boolean isStaticResourceRequest(final ServletRequest request) {
        return request.hasStaticResource() && request.getMethod() == MethodType.GET;
    }

    @Override
    public ServletResponse handle(final ServletRequest request) {
        final ModelAndView mav = ModelAndView.of(request.getPath());
        return ServletResponse.of(mav, request);
    }
}
