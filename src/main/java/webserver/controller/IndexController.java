package webserver.controller;

public class IndexController {
    private IndexController() {
    }

    private static class IndexControllerHolder {
        private static final IndexController INSTANCE = new IndexController();
    }

    public static IndexController getInstance() {
        return IndexControllerHolder.INSTANCE;
    }

    public Responsive goIndex() {
        return (request, response) -> {
            response.setContentType("text/html");
            response.setView("/index.html");
        };
    }
}
