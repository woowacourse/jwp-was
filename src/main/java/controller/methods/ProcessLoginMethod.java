//package controller.methods;
//
//import db.DataBase;
//import http.request.Request;
//import http.request.RequestMethod;
//import http.response.Response;
//import http.session.Session;
//import model.User;
//
//public class ProcessLoginMethod implements ControllerMethod {
//
//    @Override
//    public boolean isMapping(Request request) {
//        return (RequestMethod.POST == request.getRequestMethod() &&
//                "/user/login".equals(request.getUrl().getOriginalUrlPath()));
//    }
//
//    @Override
//    public void processResponse(Request request, Response response) {
//        User user = DataBase.findUserById(request.getQueryParameters().getParameter("userId"));
//
//        if (user != null && user.isCorrectPassWord(request.getQueryParameters().getParameter("password"))) {
//            Session session = request.getSession();
//            session.setAttribute("user", user.getUserId());
//
//            response.found()
//                    .putResponseHeaders("Location: ", "http://localhost:8080/index.html")
//                    .putResponseHeaders("Set-Cookie: Session-Id=", session.getSessionId() + "; Path=/");
//        }
//
//        if (user == null || !user.isCorrectPassWord(request.getQueryParameters().getParameter("password"))) {
//            response.found()
//                    .putResponseHeaders("Location: ", "http://localhost:8080/user/login_failed.html");
//        }
//    }
//}
