package http.view;

import java.util.ArrayList;
import java.util.List;

public class ViewHandler {
    private List<Resolver> resolvers;

    public ViewHandler() {
        resolvers = new ArrayList<>();
    }

    public void addResolver(Resolver viewResolver) {
        resolvers.add(viewResolver);
    }

    public byte[] handle(ModelAndView modelAndView) {
        return (byte[]) resolvers.get(0).resolve(modelAndView);
    }
}
