package domain;

import domain.controller.Controller;
import java.util.Map;

public class UrlMapper {

    private final Map<String, Controller> mapper;

    public UrlMapper(Map<String, Controller> mapper) {
        this.mapper = mapper;
    }

    public Controller getController(String url) {
        return this.mapper.get(url);
    }

    public boolean contains(String url) {
        return this.mapper.containsKey(url);
    }

}
