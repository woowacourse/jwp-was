# Servlet
## 1. 서블릿 동작과정
1. HTTP Request를 Servlet Container에 보낸다.
2. Servlet Container는 HttpServletRequest, HttpServletResponse를 생성한다.
3. 사용자가 요청한 URL을 석하여 어느 서블릿에 대한 요청인지 찾는다
4. 컨테이너는 서블릿 service() 메소드를 호출하며, POST/GET 여부에 따라 doGet() 또는 doPost()가 호출된다.
5. doGet() 이나 doPost() 메소드는 동적인 페이지를 생성한 후 HttpServletResponse 객체에 응답을 보낸다.
6. 응답이 완료되면 HttpServletRequest, HttpServletResponse 두 객체를 소멸시킨다.

## 2. 종류
1. Servlet
서블릿 프로그램을 개발할 때 반드시 구현해야하는 메서드를 선언하고 있는 인터페이스
2. GenericServlet
Servlet 인터페이스를 상속하여 필요한 기능을 구현한 추상 클래스.
service() 메서드를 제외한 모든 메서드를 재정의하여 적절한 기능으로 구현.
GenericServlet 클래스를 상속하면 애플리케이션의 프로토콜에 따라 메서드 재정의 구문을 적용해야 함
3. HttpServlet
GenericServlet을 상속받아 service를 HTTP 프로토콜 요청 메서드에 적합하게 재구현해놓음
이미 DELETE,GET,HEAD,OPTIONS,POST,PUT,TRACE를 처리하는 메서드가 모두 정의되어 있다.