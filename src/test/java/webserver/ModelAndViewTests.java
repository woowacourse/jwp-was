package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ModelAndViewTests {
    private ModelAndView modelAndView;

    private class Model {
        private String name;

        public Model(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    @BeforeEach
    void setUp() {
        modelAndView = new ModelAndView();
    }

    @Test
    void addModel() {
        modelAndView.addModel("model", new Model("model"));
        Object model = modelAndView.getModels().get("model");
        assertThat(model.toString()).isEqualTo("model");
    }
}
