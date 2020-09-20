### 요구사항 1
-[x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

### 요구사항 2
-[x] 회원가입 페이지 이동
  - “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.
-[x] 회원가입 시 입력한 정보를 파싱하여 객체로 만든다.
  - /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net 
  - HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
  
### 요구사항 3
-[x] 회원가입을 Post로 전달한다.
-[x] DB에 저장한다.