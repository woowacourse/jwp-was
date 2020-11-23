# 웹 애플리케이션 서버
## 1단계 
1. http://localhost:8080/index.html 로 접속했을 때 src/resources/static/index.html파일을 읽어 클라이언트에 응답한다.
    - 정적 자원은 src/resources/static/ 이하에서 파일을 읽어오도록 한다.
2. 회원가입 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동한다.
3. POST /user/create Http 요청을 통해 넘어오는 회원가입을 처리한다.  
4. 회원가입 완료 후 /index.html 페이지로 리다이렉트 한다. 
5. html 외, css, js, font등의 정적 자원도 서버에서 반환할 수 있도록 처리한다.