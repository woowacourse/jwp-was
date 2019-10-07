package webserver.router;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.BadRequestException;
import webserver.pageprovider.PageProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OrderedRouterTest {

    @Test
    @DisplayName("맨 뒤에 추가되는지 확인")
    void pushBack() {
        List<Router> mockRouters = new ArrayList<>();
        OrderedRouter orderedRouter = new OrderedRouter(mockRouters);

        int numLoop = 3;

        for (int i = 0; i < numLoop; i++) {
            Router pushedRouter = mock(Router.class);
            orderedRouter.pushBack(pushedRouter);
            assertThat(lastRouter(mockRouters)).isEqualTo(pushedRouter);
        }
    }

    private Router lastRouter(List<Router> routers) {
        return routers.get(routers.size() - 1);
    }

    @Test
    @DisplayName("해당 패턴을 만족하는 여러 컨트롤러 존재, 제일 먼저 등록된 컨트롤러 반환")
    void retrievePageProvider() {
        PageProvider precedencePageProvider = mock(PageProvider.class);
        PageProvider postPageProvider = mock(PageProvider.class);

        List<Router> routers = Arrays.asList(
                createNotMatchingRouter(),
                createNotMatchingRouter(),
                createMatchingRouter(precedencePageProvider),
                createNotMatchingRouter(),
                createMatchingRouter(postPageProvider)
        );

        OrderedRouter orderedRouter = new OrderedRouter(routers);

        assertThat(orderedRouter.retrieve("right pattern")).isEqualTo(precedencePageProvider);

    }

    @Test
    @DisplayName("다룰 수 없는 패턴")
    void canHandle_cannotHandle() {
        List<Router> routers = Arrays.asList(
                createNotMatchingRouter(),
                createNotMatchingRouter(),
                createNotMatchingRouter()
        );

        OrderedRouter orderedRouter = new OrderedRouter(routers);

        assertThat(orderedRouter.canHandle("pattern can not handle")).isFalse();
    }

    @Test
    @DisplayName("다룰 수 있는 패턴")
    void canHandle_canHandle() {
        List<Router> routers = Arrays.asList(
                createNotMatchingRouter(),
                createNotMatchingRouter(),
                createMatchingRouter(mock(PageProvider.class))
        );

        OrderedRouter orderedRouter = new OrderedRouter(routers);

        assertThat(orderedRouter.canHandle("pattern can handle")).isTrue();
    }

    private Router createNotMatchingRouter() {
        Router router = mock(Router.class);
        given(router.canHandle(anyString())).willReturn(false);
        given(router.retrieve(anyString())).willThrow(BadRequestException.ofPattern(""));
        return router;
    }

    private Router createMatchingRouter(PageProvider pageProvider) {
        Router router = mock(Router.class);
        given(router.canHandle(anyString())).willReturn(true);
        given(router.retrieve(anyString())).willReturn(pageProvider);
        return router;
    }
}