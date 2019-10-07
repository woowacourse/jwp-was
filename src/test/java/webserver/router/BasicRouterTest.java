package webserver.router;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.BadRequestException;
import webserver.pageprovider.PageProvider;

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
    void retrieve_hasNoMatchingPattern() {
        List<PredicatorPageProviderMatch> matches = Arrays.asList(
                PredicatorPageProviderMatch.from((pattern) -> false, mock(PageProvider.class)),
                PredicatorPageProviderMatch.from((pattern) -> false, mock(PageProvider.class))
        );
        BasicRouter router = new BasicRouter(matches);

        assertThrows(BadRequestException.class, () -> router.retrieve("not matching pattern"));
    }

    @Test
    @DisplayName("찾으려는 패턴의 컨트롤러를 반환")
    void addAndRetrieve_hasMatchingPattern() {
        int matchingIdx = 1;
        List<PredicatorPageProviderMatch> matches = Arrays.asList(
                PredicatorPageProviderMatch.from((pattern) -> false, mock(PageProvider.class)),
                PredicatorPageProviderMatch.from((pattern) -> true, mock(PageProvider.class))
        );
        BasicRouter router = new BasicRouter(matches);

        assertThat(router.retrieve("matching pattern with idx 1")).isEqualTo(matches.get(matchingIdx).getPageProvider());
    }

    @Test
    @DisplayName("추가한 컨트롤러가 잘 반환되는지")
    void addPageProvider_retrieveThatPageProvider() {
        List<PredicatorPageProviderMatch> matches = new ArrayList<>();
        BasicRouter router = new BasicRouter(matches);

        String specificPattern = "salkfjasf";
        PageProvider expectedPageProvider = mock(PageProvider.class);
        router.addPageProvider((pattern) -> specificPattern.equals(pattern), expectedPageProvider);

        assertThat(router.retrieve(specificPattern)).isEqualTo(expectedPageProvider);
    }
}