package kr.wootecat.dongle;

import kr.wootecat.dongle.model.servlet.ServletMapper;
import kr.wootecat.dongle.view.dto.ServletMappingConfig;

public class ServletConfig {

    private final ServletMappingConfig servletMappingConfig;

    public ServletConfig(ServletMappingConfig servletMappingConfig) {
        this.servletMappingConfig = servletMappingConfig;
    }

    void init() {
        ServletMapper mapper = ServletMapper.getInstance();
        mapper.putAll(servletMappingConfig.getServletMapping());
    }
}
