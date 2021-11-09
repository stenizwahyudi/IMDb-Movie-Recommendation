package app.dal;


import app.model.Users;
import app.model.WatchList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;



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
@WebServlet("/addToWatchList")
public class WatchListServlet extends HttpServlet {

  protected WatchListDao watchListDao;
  protected RecommendationDao recommendationDao;

  @Override
  public void init() throws ServletException {
    watchListDao = WatchListDao.getInstance();
    recommendationDao = RecommendationDao.getInstance();
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    String recommendationId = req.getParameter("id");
//    System.out.println(recommendationId);
    HttpSession mySession = req.getSession(false);
    Users users = (Users) mySession.getAttribute("user");
    boolean inWatchList = false;

    List<WatchList> watchLists;

    // Retrieve BlogUsers, and store as a message.
    try {
      watchLists = watchListDao.getWatchListByUserName(users.getUserName());
      for(WatchList watchList: watchLists){
        if(watchList.getRecommendation().getRecommendationId().equals(recommendationId)){
          inWatchList = true;
          break;
        }
      }
      req.setAttribute("inWatchList", inWatchList);
      if(!inWatchList){
        watchListDao.create(new WatchList(users,recommendationDao.getRecommendationByRecommendationId(recommendationId),0));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }


    resp.getWriter().write(String.valueOf(inWatchList));
  }

}
