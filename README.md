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
 - [x] stylesheet 파일을 지원하도록 구현
   ```
   GET ./css/style.css HTTP/1.1
   Host: localhost:8080
   Accept: text/css,*/*;q=0.1
   Connection: keep-alive
   ```
   
### 🚀 2단계 - HTTP 웹 서버 리팩토링

#### WAS 기능 요구사항
- [x] 다수의 사용자 요청에 대해 Queue에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
- [x] 서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. 
Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.

#### HTTP 요청/응답 처리 기능
역할을 분리해 재사용 가능하도록 한다.
- [x] HTTP 요청 Header/Body 
- [x] HTTP 응답 Header/Body

#### 다형성을 활용해 분기처리 제거
- [x] 요청과 응답에 대한 처리를 추상화 

#### 리뷰 반영
- [x] 지원하는 메서드만 오버라이딩 해서 사용하도록 수정
- [x] resource 처리 영역과 아닌 영역을 구분하여 response를 반환
- [x] 다양한 resource postfix 형식에 대한 테스트 작성

### 🚀 3단계 - 로그인 및 세션 구현

#### 요구사항 1
- [x] 로그인 기능 구현
    - 성공시
        - [x] /index.html로 이동
        - [x] Cookie Header 값이 logined=true 
    - 실패시
        - [x] /user/login_failed.html로 이동
        - [x] Cookie Header 값이 logined=false 
 
#### 요구사항 2
- [x] 유저 목록 출력하기
    - [x] 로그인 상태일 경우 목록 출력
    - [x] 로그인하지 않은 상태면 로그인 페이지로 이동

#### 요구사항 3
- [x] HttpSession 생성하여 적용하기
    - [x] String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
    - [x] void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
    - [x] Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
    - [x] void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
    - [x] void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제
- [x] cookie 구조화

#### 리뷰 반영
- [x] 인코딩 실패 시 errorStack 출력 -> InternalServerError 반환 
- [x] null 반환 제거
- [x] doGet() 템플릿화하어 재사용 가능한 구조를 만들기

### 🚀 4단계 - multi module 적용

#### 요구사항
- [x] Web Application Server(이하 WAS) 본연의 기능을 담당하는 부분 분리 사용자 관리라는 응용 애플리케이션을 구현하는 부분 분리

