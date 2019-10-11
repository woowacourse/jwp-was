# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

## 1단계 - HTTP 웹 서버 구현
1. http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
    - [x] Request Header 출력하기
    - [x] Header의 첫번째 라인에서 요청 URL 추출하기
    - [x] path에 해당하는 파일 읽어서 응답하기
2. "회원가입" 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
    - [x] "회원가입" 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동한다.
    - [x] 요청 URL에서 접근 경로와 이름=값을 추출
    - [x] 추출한 이름=값을 User 클래스에 담는다.
3. http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
    - [x] Post 요청인 경우 Request Body에서 회원가입시 입력한 모든 데이터를 추출해 User 객체를 생성한다.
4. "회원가입"을 완료하면 /index.html 페이지로 이동한다.
    - [x] "회원가입"을 완료하면 /index.html 페이지로 리다이렉트한다. (302 code 사용)
5. 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
    - [x] Stylesheet인 경우 응답 헤더의 Content-Type을 text/css로 전송한다. (Accept로 Stylesheet 판단)
    
## 1단계 리펙터링
1. HttpRequest 리펙토링
    - [x] Http 요청을 Parsing하는 기능 분리
    - [x] QueryParams 리펙토링
2. Response 생성 기능 분리
    - [x] Status에 따라 Http Response 만드는 기능 분리
3. RequestHandler 리펙토링
    - [x] controller 분리
    - [x] ResponseEntity 분리
4. ResponseEntity 제거
    - [x] Contorller에서 request와 response를 직접 받도록 수정
5. StaticResourceHandler에서 response를 forward하도록 수정

## 2단계 로그인 및 세션 구현
1. 로그인 기능 구현
    - [x] '/user/login'으로 로그인 요청 받기
    - [x] 로그인이 성공하면 index.html로 이동
    - [x] Cookie 구현 
    - [x] 로그인이 성공하면 응답의 cookie header값을 logined=true로 전달
    - [x] 로그인이 실패하면 cookie header값을 logined=false로 전달
        
2. 로그인상태일 경우 사용자 목록 출력
    - [x] '/user/list'로 요청 받기
    - [x] 유저 목록을 받아와 template 파일을 동적으로 생성
   
3. HttpSession 구현
    - [ ] getId() 구현
    - [ ] setAttribute(String name, Object value) 구현
    - [ ] getAttribute(String name) 구현
    - [ ] removeAttribute(String name) 구현
    - [ ] invalidate() 구현
    - [ ] 서버에서 요청에 대한 세션을 처음 필요로 하는 시점에 Session을 만들어준다.
    - [ ] Header에 쿠키가 없거나, 쿠키 안에 JSESSIONID가 없으면 새로운 세션을 발급한다.
    - [ ] request에 getSession 구현
    - [ ] SessionManager 구현

4. 리펙토링
    - [ ] HttpReqeust에 빌더 패턴 적용해볼 것
    
