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

class BasicRouterTest {
    @Test
    void 싱글톤인지() {
        assertThat(BasicRouter.getInstance() == BasicRouter.getInstance()).isTrue();
    }

    @Test
    @DisplayName("찾으려는 패턴의 컨트롤러가 존재하지 않을 경우")
    void retrieveController_hasNoMatchingPattern() {
        List<PredicatorControllerMatch> matches = Arrays.asList(
                PredicatorControllerMatch.from((pattern) -> false, mock(Controller.class)),
                PredicatorControllerMatch.from((pattern) -> false, mock(Controller.class))
        );
        BasicRouter router = new BasicRouter(matches);

        assertThrows(BadRequestException.class, () -> router.retrieveController("not matching pattern"));
    }

    @Test
    @DisplayName("찾으려는 패턴의 컨트롤러를 반환")
    void addAndRetrieve_hasMatchingPattern() {
        int matchingIdx = 1;
        List<PredicatorControllerMatch> matches = Arrays.asList(
                PredicatorControllerMatch.from((pattern) -> false, mock(Controller.class)),
                PredicatorControllerMatch.from((pattern) -> true, mock(Controller.class))
        );
        BasicRouter router = new BasicRouter(matches);

        assertThat(router.retrieveController("matching pattern with idx 1")).isEqualTo(matches.get(matchingIdx).getController());
    }

    @Test
    @DisplayName("추가한 컨트롤러가 잘 반환되는지")
    void addController_retrieveThatController() {
        List<PredicatorControllerMatch> matches = new ArrayList<>();
        BasicRouter router = new BasicRouter(matches);

        String specificPattern = "salkfjasf";
        Controller expectedController = mock(Controller.class);
        router.addController((pattern) -> specificPattern.equals(pattern), expectedController);

        assertThat(router.retrieveController(specificPattern)).isEqualTo(expectedController);
    }
}