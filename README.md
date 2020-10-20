## 1단계 - HTTP 웹 서버 구현

- 요구사항 1
    - [x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
- 요구사항 2
    - [x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
    - [x] HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
- 요구사항 3
    - [x] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
- 요구사항 4
    - [x] “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
- 요구사항 5
    - [x] 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
        
## 2단계 - HTTP 웹 서버 리팩토링
- WAS 기능 요구사항
    - [x] 다수의 사용자 요청에 대해 Queue 에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
    - [x] 서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.
- HTTP 요청/응답 처리 기능
    - [x] HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.
- 코드 리팩토링 1단계
    - [x] RequestHandler 클래스의 책임을 분리한다.
- 코드 리팩토링 2단계
    - [x] 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpRequest)
    - [x] 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpResponse)
    - [x] 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.
    
## 3단계 - 로그인 및 세션 구현
1. - [x] 회원가입한 사용자로 로그인을 할 수 있다.
   - [x] 로그인 성공 시 쿠키를 활용해 로그인 상태를 유지한다.
2. - [x] 로그인 상태일 경우 사용자 목록을 출력한다.
   - [ ] 로그인하지 않은 상태일 경우 로그인 페이지로 이동한다.
3. HttpSession API의 일부를 작성한다
    - [ ] getId()
    - [ ] setAttribute(String name, Object value)
    - [ ] getAttribute(String name)
    - [ ] removeAttribute(String name)
    - [ ] invalidate()