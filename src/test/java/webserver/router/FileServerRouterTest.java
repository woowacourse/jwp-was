package webserver.router;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.PageTemplateController;
import webserver.pageprovider.FilePageProvider;

import static org.assertj.core.api.Assertions.assertThat;

class FileServerRouterTest {
    private final static String FILE_PATH_AFTER_RESOURCES = "/resources_test.txt";
    private final static String FILE_PATH_AFTER_STATIC = "/resources_static_test.txt";
    private final static String FILE_PATH_AFTER_TEMPLATES = "/resources_templates_test.txt";

    @Test
    @DisplayName("루트가 resources 인 경우, prefix == \"\"")
    void retrieveController_canHandle_filePathAfterResources() {
        assertThat(FileServerRouter.getInstance().retrieveController(FILE_PATH_AFTER_RESOURCES))
                .isEqualTo(PageTemplateController.ofGetPageProvider(FilePageProvider.fromDirectory("")));
    }

    @Test
    @DisplayName("루트가 resources/static 인 경우, prefix == \"static\"")
    void retrieveController_canHandle_filePathAfterStatic() {
        assertThat(FileServerRouter.getInstance().retrieveController(FILE_PATH_AFTER_STATIC))
                .isEqualTo(PageTemplateController.ofGetPageProvider(FilePageProvider.fromDirectory("static")));
    }

    @Test
    @DisplayName("루트가 resources/templates 인 경우, prefix == \"templates\"")
    void retrieveController_canHandle_filePathAfterTemplates() {
        assertThat(FileServerRouter.getInstance().retrieveController(FILE_PATH_AFTER_TEMPLATES))
                .isEqualTo(PageTemplateController.ofGetPageProvider(FilePageProvider.fromDirectory("templates")));
    }

    @Test
    @DisplayName("루트가 resources 인 파일을 찾을 수 있는지")
    void canHandle_filePathAfterResources() {
        assertThat(FileServerRouter.getInstance().canHandle(FILE_PATH_AFTER_RESOURCES)).isTrue();
    }

    @Test
    @DisplayName("루트가 resources/static 인 파일을 찾을 수 있는지")
    void canHandle_filePathAfterStatic() {
        assertThat(FileServerRouter.getInstance().canHandle(FILE_PATH_AFTER_STATIC)).isTrue();
    }

    @Test
    @DisplayName("루트가 resources/templates 인 파일을 찾을 수 있는지")
    void canHandle_filePathAfterTemplates() {
        assertThat(FileServerRouter.getInstance().canHandle(FILE_PATH_AFTER_TEMPLATES)).isTrue();
    }
}