## service-app module
- 웹 애플리케이션을 수행하기 위한 표현계층과 응용계층 영속계층이 정의된다. (현재 응용계층은 존재하지 않는다.)
- web-domain, web-server 모듈을 참조하고 있다.
- UrlAppender에서 enum으로 정의된 url주소와 Controller를 한 쌍으로 지정하고, web-server에서 제공하는 RequestMapper에 내용이 반영된다.
