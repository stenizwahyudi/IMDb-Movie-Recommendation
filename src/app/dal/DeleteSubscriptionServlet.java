package app.dal;


import app.model.UserSubscriptions;
import app.model.Users;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;



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
@WebServlet("/deleteSubscription")
public class DeleteSubscriptionServlet extends HttpServlet {

  protected UserSubscriptionsDao userSubscriptionsDao;
  protected PlatformsDao platformsDao;

  @Override
  public void init() throws ServletException {
    userSubscriptionsDao = UserSubscriptionsDao.getInstance();
    platformsDao = PlatformsDao.getInstance();
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
  

    String subscriptionId = req.getParameter("id");
    System.out.println(subscriptionId);
    int subId = Integer.parseInt(subscriptionId);
    
    System.out.println(subId);
    HttpSession mySession = req.getSession(false);
    Users users = (Users) mySession.getAttribute("user");
    boolean deletedSubscription = false;

    UserSubscriptions userSubscription;

    // Retrieve BlogUsers, and store as a message.
    try {
    	
      userSubscription = userSubscriptionsDao.getUserSubscriptionsById(subId);
      userSubscriptionsDao.delete(userSubscription);
      req.setAttribute("userSubscription", userSubscription);
      req.setAttribute("deletedSubscription", deletedSubscription);
     
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }


    resp.getWriter().write(String.valueOf(deletedSubscription));
    req.getRequestDispatcher("/UserPage.jsp").forward(req, resp);
  }

}
