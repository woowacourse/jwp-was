package webserver.servlet;

public class UserCreate implements Servlet{
    @Override
    public void service() {
        System.out.println();
    }

    @Override
    public String getResourcePathToRedirect() {
        return "./templates/index.html";
    }
}
