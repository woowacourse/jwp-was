# 웹 애플리케이션 서버

## 1단계 - HTTP 웹 서버 구현
### 요구 사항
- [x] http://localhost:8080/index.html 로 접속했을 때 templatkes 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다
  - [x] Request Header 읽는다.
  - [x] Request Header에서 path 추출한다.
  - [x] path에 해당하는 파일을 찾아 읽고 응답한다.
- [x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
```text
/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```
  - [x] HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱한다.
  - [x] 파싱한 값을 model.User 클래스에 저장한다.
  - [x] 파싱한 User를 DataBase에 저장한다.
- [x] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
  - [x] form.html 파일의 form 태그 method를 get에서 post로 수정한다.
  - [x] 요청 정보에서 Method를 추출한다.
  - [x] Body에 담긴 파라미터를 추출해서 User 클래스에 저장한다.
- [x] “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
- [x] 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
  - [x] static 파일을 요청하는 경우인지 판단한다.
  - [x] 응답 헤더에  Content-Type을 추가한다.