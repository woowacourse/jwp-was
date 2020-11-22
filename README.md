# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

### 기능 목록

요구사항 1

-[x]  http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
    -[x] 모든 Request Header를 출력한다.
    -[x] Request Line에서 path를 분리한다.
    -[x] path에 해당하는 파일 읽어 응답한다.
    
요구사항 2

-[x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
    - [x] HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
    - [x] Request Parameter 추출한다.
    
요구사항 3

-[x] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
    -[x] Request Body의 값 추출
    
요구사항 4

-[x] “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
    -[x] HTTP 응답 헤더의 status code를 200이 아니라 302 code를 사용한다.
    
요구사항 5

-[x] 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.

WAS 기능 요구사항

-[x] 다수의 사용자 요청에 대해 Queue에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.

리팩토링

-[x] RequestHandler 클래스의 책임을 분리한다.
    -[x] 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpRequest)
    -[x] 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpResponse)
    -[x] 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 한다.

로그인 및 세션 구현

-[x] 로그인을 구현한다.
    -[x] 로그인 메뉴를 클릭해서 로그인 페이지로 이동한다.
    -[x] 회원가입을 한 사용자로 로그인할 수 있어야 한다.
    -[x] 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다.
    -[x] 로그인이 성공할 경우 요청 header의 cookie header 값이 logined=true, 실패하면 logined=false로 전달되어야 한다.
    -[x] Set-Cookie 설정 시 모든 요청에 대해 Cookie가 처리가 가능하도록 Path 설정 값을 Path=/로 설정한다.

-[x] /user/list 요청을 처리한다.
    -[x] 로그인 상태일 경우(Cookie 값이 logined=true) 사용자 목록을 출력한다.
    -[x] 로그읺하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.
    
-[ ] HttpSession API의 일부를 지원한다.
    -[ ] getId() 구현
    -[ ] setAttribute(String name, Object value) 구현
    -[ ] getAttribute(String name) 구현
    -[ ] removeAttribute(String name) 구현
    -[ ] invalidate() 구현
