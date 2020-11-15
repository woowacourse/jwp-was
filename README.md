# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

## 요구사항

### 1단계

#### 요구사항 1
 - [x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
 
#### 요구사항 2
 - [x] 회원가입
    - [x] http://localhost:8080/user/form.html 으로 이동해서 회원가입 할 수 있다.
   ```
   /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
   ```
    - [x] HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
   ```
   GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1
   Host: localhost:8080
   Connection: keep-alive
   Accept: */*
   ```
#### 요구사항 3
 - [x] 회원가입 기능을 post 요청으로 바꾸어도 정상적으로 동작하도록 구현한다.
   ```
   POST /user/create HTTP/1.1
   Host: localhost:8080
   Connection: keep-alive
   Content-Length: 59
   Content-Type: application/x-www-form-urlencoded
   Accept: */*

   userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
   ```
   
#### 요구사항 4
 - [x] 회원가입을 완료한 후 index.html로 이동해야 한다.
   ```
   '회원가입'을 완료하면 /index.html 페이지로 이동하고 싶다.
   현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다.
   따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다.
   즉, 브라우저의 URL이 /index.html로 변경해야 한다.
   ```

#### 요구사항 5
 - [ ] stylesheet 파일을 지원하도록 구현
   ```
   GET ./css/style.css HTTP/1.1
   Host: localhost:8080
   Accept: text/css,*/*;q=0.1
   Connection: keep-alive
   ```