# 웹 애플리케이션 서버
## 요구사항
- [x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
- [x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
- [x] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
- [x] “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
- [x] 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
- [ ] “로그인” 메뉴 클릭하면 /user/login.html 으로 이동해 로그인할 수 있다. 로그인이 성공 시 index.html로 이동, 로그인이 실패 시 /user/login_failed.html로 이동한다.
- [ ] 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지 한다. 로그인이 성공 시 요청 header의 Cookie 값이 logined=true, 로그인이 실패하면 Cookie 값이 logined=false로 전달되어야 한다.
- [ ] 사용자가 “로그인” 상태일 경우 /user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지로 이동한다.
- [ ] 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.
- [ ] 서블릿에서 지원하는 HttpSession API의 일부를 지원 한다.
    - [ ] String getId() : 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
    - [ ] void setAttribute(String name, Object value) : 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
    - [ ] Object getAttribute(String name) : 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
    - [ ] void removeAttribute(String name) : 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
    - [ ] void invalidate() : 현재 세션에 저장되어 있는 모든 값을 삭제
- [ ] 세션의 고유 아이디는 JDK에서 제공하는 UUID 클래스를 사용해 고유한 아이디를 활용한다.
    - [ ] 세션 관리를 위한 자료구조는 Map을 사용하며, Map<String, HttpSession>와 같은 구조가 된다. 이 때, 키는 UUID이다.
