# 웹 애플리케이션 서버
## convention

* git: https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716
* branch: step[?]-requirements[?] 형식



## TODO

요구사항 1

- [x] request Header 전체 출력
- [x] request Header에서 url path 추출
- [x] file 읽기
- [x] localhost:8080 으로 접속해도 자동으로 /index.html로 렌더링
- [x] HttpRequest class 생성(리팩토링)
- [ ] httpRequestUrlPathException 생성
- [ ] http error response 처리



요구사항2

- [x] get request param 추출
- [x] param 추출해서 User만들기



요구사항3

- [x] post로 변경



요구사항4

- [x] response 객체 만들기
- [x] response 302 메소드 만들기
- [x] 회원가입시 index.html로 리다이렉트



요구사항5

- [x] static 파일일 시 경로 변경
- [x] response header의 Content-Type 값을 보내주는 파일의 타입으로 설정하게 변경

리팩토링
- [x] servlet 생성
- [x] http response status code enum으로 변경
