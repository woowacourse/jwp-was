# 웹 애플리케이션 서버

# 1단계 - HTTP 웹 서버 구현

## 요구사항 1
- [x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

## 요구사항 2
- [x] 회원 가입 메뉴를 통해 회원가입 할 수 있다.

## 요구사항 3
- [x] 회원 가입을 POST를 통해 할 수 있다.

## 요구사항 4
- [x] 회원 가입 후 302를 보낸다.
- [x] 회원 가입 후 index.html로 리다이렉트 한다.

## 요구사항 5
- [x] Stylesheet 파일을 지원하도록 구현한다. 

# 2단계 - HTTP 웹 서버 리팩토링
- [x] 다수의 사용자 요청에 대해 Queue에 저장한 후 순차적으로 처리가 가능하도록 한다.
- [x] 요청에 대한 Thread를 매번 새로 생성하지 않고 TreadPool을 이용한다.
- [x] 정적 파일에 관한 요청을 분리해 처리한다
- [x] 응답 객체를 구현한다

# 3단계 - 로그인 및 세션 구현

## 요구사항 1
- [x] 로그인 할 수 있다.
    - [x] 로그인 성공 시, /index.html로 리다이렉트 한다.
    - [x] 로그인 성공 시, 성공 쿠키를 전달한다.
    - [x] 로그인 실패 시, /user/login_failed.html로 리다이렉트 한다.
    - [x] 로그인 실패 시, 실패 쿠키를 전달한다.

## 요구사항 2
- [x] 유저 리스트를 볼 수 있다.
    - [x] 로그인이 되어있다면 보여준다.
    - [x] 로그인이 되어있지 않다면 로그인 페이지로 이동한다.
    - [x] handlebar를 사용한다.

## 요구사항 3
- [x] HttpSession API의 일부를 구현한다.
    - [x] getId()
    - [x] setAttribute(String name, Object value)
    - [x] getAttribute(String name)
    - [x] removeAttribute(String name)
    - [x] invalidate()
    - [x] 세션 id는 쿠키를 활용해 공유한다.
    - [x] 세션 id는 UUID를 사용한다.
