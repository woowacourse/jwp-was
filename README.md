# 웹 애플리케이션 서버

## 요구사항
- Http 웹 서버 구현
    - http://localhost:8080/index.html 접속하면 webapp 디렉토리의 index.html 파일을 읽어서 클라이언에 응답
    - 회원가입 메뉴 클릭하면 /user/form.html 로 이동
    - 사용자가 입력한 회원가입 정보가 서버로 이동
    - Post 메서드로 변경하여 회원가입 처리하기
    - 회원가입 완료하면 /index.html 로 리다이렉션
    - StyleSheet 파일 응답하기
    
- 로그인
    - 로그인 메뉴 클릭하면 /user/login.html 로 이동
    - 로그인 성공 시 /index.html 로 이동
    - 로그인 실패 시 /user/login_failed.html 로 이동
    - 로그인 성공하면 로그인 상태를 유지할 수 있어야 한다.

- 사용자 목록
    - /user/list 로 이동하면 사용자가 로그인한 상태일 경우 사용자 목록 출력
        - 사용자가 로그인 상태가 아닐 경우 로그인 페이지(/user/login.html) 이동
        
- HttpSession API 구현