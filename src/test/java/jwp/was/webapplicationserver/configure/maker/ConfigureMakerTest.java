package jwp.was.webapplicationserver.configure.maker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import jwp.was.webapplicationserver.configure.ConfigureMaker;
import jwp.was.webapplicationserver.configure.annotation.Controller;
import org.junit.jupiter.api.Test;

class ConfigureMakerTest {

    @Test
    void getControllerInstances() {
        ConfigureMaker configureMaker = ConfigureMaker.getInstance();
        Set<Object> controllerInstances = configureMaker.getControllerInstances();

        for (Object controllerInstance : controllerInstances) {
            assertThat(controllerInstance.getClass().isAnnotationPresent(Controller.class));
        }
    }
}
