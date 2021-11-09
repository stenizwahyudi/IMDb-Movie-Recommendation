/**
 * Reference: https://www.codejava.net/coding/how-to-code-login-and-logout-with-java-servlet-jsp-and-mysql#LoginServlet
 */

package app.dal;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.model.Users;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long mySerialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UsersDao userDao = new UsersDao();
        try {
            Users user = userDao.checkLogin(email, password);
            String myDestPage = "Login.jsp";
            if (user != null) {
                HttpSession mySession = request.getSession(true);
                mySession.setAttribute("user", user);
                myDestPage = "Search.jsp";
            } else {
                String invalidMessage = "Invalid email or password";
                request.setAttribute("message", invalidMessage);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(myDestPage);
            dispatcher.forward(request, response);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
