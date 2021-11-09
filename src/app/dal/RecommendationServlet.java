package app.dal;

import java.io.IOException;
import java.sql.SQLException;

import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.model.Recommendation;
import app.model.UserSubscriptions;
import app.model.Users;


/**
 * FindUsers is the primary entry point into the application.
 * <p>
 * Note the logic for doGet() and doPost() are almost identical. However, there is a difference:
 * doGet() handles the http GET request. This method is called when you put in the /findusers URL in
 * the browser. doPost() handles the http POST request. This method is called after you click the
 * submit button.
 * <p>
 * To run: 1. Run the SQL script to recreate your database schema: http://goo.gl/86a11H. 2. Insert
 * test data. You can do this by running blog.tools.Inserter (right click, Run As > JavaApplication.
 * Notice that this is similar to Runner.java in our JDBC example. 3. Run the Tomcat server at
 * localhost. 4. Point your browser to http://localhost:8080/BlogApplication/findusers.
 */
@WebServlet("/recommendation")
public class RecommendationServlet extends HttpServlet {

  private RecommendationDao recommendationDao;
  private UserSubscriptionsDao userSubscriptionsDao;
  @Override
  public void init() throws ServletException {
    recommendationDao = RecommendationDao.getInstance();
    userSubscriptionsDao = UserSubscriptionsDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.

    Recommendation recommendation;
    List<UserSubscriptions> userSubscriptions;

    String recommendationId = req.getParameter("id");
    HttpSession mySession = req.getSession(false);
    Users users = (Users) mySession.getAttribute("user");
    // Retrieve BlogUsers, and store as a message.
    try {
      recommendation = recommendationDao.getRecommendationByRecommendationId(recommendationId);
      userSubscriptions = userSubscriptionsDao.getUserSubscriptionByUserName(users.getUserName());
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }

    req.setAttribute("recommendation", recommendation);

    req.getRequestDispatcher("/Recommendation.jsp").forward(req, resp);
  }

}
