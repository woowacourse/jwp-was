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
- [x] http error response 처리



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
- [x] 쓰레드 풀 변경

피드백

- [x] servlet 싱글톤 생성으로 변경
- [x] map에서 get() 대신 getOrDefault() 사용으로 변경
- [x] ```header.getHeader("Accept").contains("text/html")``` 개선 -> header에 함수 추가
- [x] 파일 경로 붙여주는걸 HttpRequest에서 하지 말고 Servlet에서 하게 변경(```HttpRequestUtils.generateTemplateFilePath```)
- [ ] RequestUri는 자바에서 제공하는 URI 클래스 사용해보기
- [x] http status가 OK가 아니면 dos.flush()가 수행되지 않는 문제 해결 -> docs.flush()를 맨 뒤로 빼기
- [x] 서블릿에서 사용할 response header 클래스 만들어보기
- [x] doPost에서 사용 안하는 body 변수 제거

피드백

- [ ] servlet 싱글톤 다시 변경
- [ ] FileServlet 한 번만 생성되게 변경
- [ ] file일 경우만 FileServlet으로 가게 변경
- [x] response 만드는 부분 메소드 추출하기
