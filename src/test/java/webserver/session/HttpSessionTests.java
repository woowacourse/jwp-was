package webserver.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.controller.session.HttpSession;
import webserver.controller.session.UUIDGenerator;

import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class HttpSessionTests {
    private HttpSession httpSession;
    private UUID uuid;

    private class Model {
        private String name;

        public Model(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Model model = (Model) o;
            return Objects.equals(name, model.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    @BeforeEach
    void setUp() {
        uuid = UUIDGenerator.generateUUID();
        httpSession = new HttpSession(uuid);
    }

    @Test
    void uuid() {
        assertThat(httpSession.getUuid()).isEqualTo(uuid);
    }

    @Test
    void getAttribute() {
        Model model = new Model("model");

        httpSession.setAttributes("model", new Model("model"));
        Object sessionObject = httpSession.getAttribute("model");
        assertThat(model).isEqualTo(sessionObject);
    }

    @Test
    void removeAttribute() {
        HttpSession newHttpSession = new HttpSession(UUID.randomUUID());
        newHttpSession.setAttributes("model", new Model("remove"));
        newHttpSession.removeAttribute("model");
        assertThat(newHttpSession.isEmpty()).isTrue();
    }

    @Test
    void invalidate() {
        UUID uuid = UUID.randomUUID();
        HttpSession newHttpSession = new HttpSession(uuid);

        newHttpSession.setAttributes("model", new Model("invalidate"));
        newHttpSession.setAttributes("model2", new Model("invalidate2"));
        newHttpSession.setAttributes("model3", new Model("invalidate3"));
        newHttpSession.invalidate();
        assertThat(newHttpSession.isEmpty()).isTrue();
        assertThat(newHttpSession.getUuid()).isEqualTo(uuid);
    }
}
