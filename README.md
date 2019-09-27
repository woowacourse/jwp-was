# 웹 애플리케이션 서버

## Step - 1
- 요구사항
    1. `http://localhost:8080/index.html`로 접속했을 때, `index.html` 파일을 읽어 응답으로 보낸다.
    2. 정적 파일 요청에 대해서 `/static` 디렉터리 내의 파일을 찾아 응답으로 보낸다.
    3. 메인 페이지에서 `/user/form.html`로 이동할 수 있어야 하며, 폼의 POST 요청을 받아 `User` 클래스에 저장한다.
    4. 회원가입 완료 후, `/index.html`페이지로 redirect 방식으로 페이지를 이동한다.
    
## Step - 2
- 요구사항
    1. 로그인 메뉴를 누르면 `http://localhost:8080/user/login.html`로 이동한다.
    2. 로그인 성공 시 `/index.html`로 실패시 `/user/login_failed.html`로 이동한다.
    3. 로그인 이후에 Cookie를 이용해 로그인을 유지한다.
    4. 로그인 상태에서 `http://localhost:8080/user/list`로 접속할 경우 사용자 목록 페이지로, 로그인 상태가 아닐 경우, 로그인 페이지로 이동한다.
    5. 세션의 기능을 지원한다.