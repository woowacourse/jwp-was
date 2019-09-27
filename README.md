# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

## To-Do-List
- [x] HttpRequest, HttpResponse 가 in/out stream 을 사용하지 않도록 리팩토링
    - in/out stream 에서 데이터를 읽거나 쓰는 책임은 HttpRequest / HttpResponse 가 갖는 것보다 별도의 클래스가 갖는 것이 낫다.
    - 점진적인 리팩토링을 통해 in/out 에서 문자열로 리팩토링한다.
        - 기존 코드를 유지한 채 리팩토링한 코드를 추가, 점진적으로 리팩토링한 코드를 사용하도록 기존 코드를 변경

- [x] IntelliJ 의 패키지 리팩토링으로 인해 정적 파일들의 relative urls 가 이상하게 바뀜. 원상태로 되돌린다.
    - was.~ -> ~

- [x] Thread Pool 사용해 한정된 쓰레드만 생성
    - ThreadPool 인터페이스
        - execute()
    - BasicThreadPool 객체

- [x] 세션 기능
    - UUID 클래스를 사용해 세션 고유 아이디 생성
    - Session 객체는 Map<String, Object> 를 갖는 일급 컬레션
    - Map<UUID, Session> 을 갖는 SessionHandler 클래스
    
- [x] 로그인 서블릿
    - ID, PASSWORD 검증
    - 세션에 생성하고 세션 ID Response Header 에 추가

- [x] RequestMapping 기능 구현
    - 자바 리플렉션으로 어노테이션 스캔
    
- [x] 쿠키 파싱
    - HttpRequest 에서 헤더를 파싱할 때 쿠키를 별도로 파싱하여 맵으로 관리

- [ ] 세션 생성 시기 리팩토링
    - 세션을 도메인 코드가 처음 사용할 때 생성하도록 (굳이 로그인이 아니어도)
    - getSession() 할때 만들면 될듯

- [ ] View, ViewResolver 클래스 생성
    - RedirectView, ForwardView 등
    - Servlet 에서 리턴하고, View 가 HttpResponse 를 생성하는 역할을 맡는다.

- [ ] 예외 처리
    - AbstractServlet 을 상속받는 RestController, Controller 클래스 생성
    - RestController 클래스는 발생한 예외를 RestException 으로 감싸서 리턴한다.
    - ErrorView, ErrorHandler 클래스 생성
    - Servlet 이 View 를 리턴하도록 리팩토링
    - RequestHandler 에서 View 를 이용해 HttpResponse 를 생성하도록 리팩토링

- [ ] 테스트 코드 보강
    - 컨트롤러 테스트 (인수 테스트)
    - mvc 테스트
    - was 테스트
    - web server 테스트
