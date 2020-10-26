package web.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import web.application.controller.Controller;
import web.server.dto.UrlMappingCreateDto;

public class UrlMapper {

    private final Map<String, Controller> mapper;

    public UrlMapper(List<UrlMappingCreateDto> urlMappingCreateDtos) {
        this.mapper = new HashMap<>();
        for (UrlMappingCreateDto urlMappingCreateDto : urlMappingCreateDtos) {
            this.mapper.put(urlMappingCreateDto.getUrl(), urlMappingCreateDto.getController());
        }
    }

    public Controller getController(String url) {
        return this.mapper.get(url);
    }

    public boolean contains(String url) {
        return this.mapper.containsKey(url);
    }
}
