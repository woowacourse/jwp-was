package webserver.router;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.BadRequestException;
import webserver.controller.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class RouterTest {
    @Test
    void 싱글톤인지() {
        assertThat(Router.getInstance() == Router.getInstance()).isTrue();
    }

    @Test
    @DisplayName("찾으려는 패턴의 컨트롤러가 존재하지 않을 경우")
    void retrieveController_hasNoMatchingPattern() {
        List<Router.PredicatorControllerMatch> matches = Arrays.asList(
                new Router.PredicatorControllerMatch((pattern) -> false, mock(Controller.class)),
                new Router.PredicatorControllerMatch((pattern) -> false, mock(Controller.class))
        );
        Router router = new Router(matches);

        assertThrows(BadRequestException.class, () -> router.retrieveController("not matching pattern"));
    }

    @Test
    @DisplayName("찾으려는 패턴의 컨트롤러를 반환")
    void addAndRetrieve_hasMatchingPattern() {
        int matchingIdx = 1;
        List<Router.PredicatorControllerMatch> matches = Arrays.asList(
                new Router.PredicatorControllerMatch((pattern) -> false, mock(Controller.class)),
                new Router.PredicatorControllerMatch((pattern) -> true, mock(Controller.class))
        );
        Router router = new Router(matches);

        assertThat(router.retrieveController("matching pattern with idx 1")).isEqualTo(matches.get(matchingIdx).controller);
    }

    @Test
    @DisplayName("추가한 컨트롤러가 잘 반환되는지")
    void addController_retrieveThatController() {
        List<Router.PredicatorControllerMatch> matches = new ArrayList<>();
        Router router = new Router(matches);

        String specificPattern = "salkfjasf";
        Controller expectedController = mock(Controller.class);
        router.addController((pattern) -> specificPattern.equals(pattern), expectedController);

        assertThat(router.retrieveController(specificPattern)).isEqualTo(expectedController);
    }
}