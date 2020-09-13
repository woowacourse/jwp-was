# 웹 애플리케이션 서버

- [x] 요구사항 1 <br>
      http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
      
- [x] 요구사항 2 <br>
      “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
      - [x] 요청 받은 url정보를 분리 한다.
      - [x] 분리된 url의 요청이 회원가입 요청인지 확인한다.
       - [x] 회원 가입일 경우 유저 정보를 분리 한다.
      - [x] 유저를 저장한다.
      - [x] 메인 화면으로 돌아간다.
      
- [x] 요구사항 3 <br>
     http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
     
- [ ] 요구사항 4 <br>
       “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
       
- [ ] 요구사항 5
      지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.