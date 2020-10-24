# 1단계
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

### 요구사항 4
-[x] 회원가입 후 redirect한다.

### 요구사항 5
-[x] css,js도 지원한다. 


#2단계
 HTTP 요청/응답 처리 기능은 애플리케이션 개발자가 신경쓰지 않아도 재사용이 가능한 구조로 만들기
-[x] 다수의 사용자 요청에 대해 Queue 에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
  - 서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.
-[x] Controller 추상화
-[x] HTTP 요청/응답 처리 기능
  - HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.
  
#3단계
-[x] 로그인 처리
    - 성공
        -[ ] index.html로 이동
        -[ ] Cookie header logined=true
    - 실패
        -[ ] /user/login_failed.html로 이동
        -[ ] Cookie header logined=false
-[ ] 사용자 목록 조희
    -[ ] 로그인했다면 출력한다.
    -[ ] 로그인하지 않았다면 로그인 페이지로 이동
-[ ] 세션 구현       
    String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환  
    void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장  
    Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환  
    void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제  
    void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제 