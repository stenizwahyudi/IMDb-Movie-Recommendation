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
@WebServlet("/updateWatchList")
public class WatchListServletUpdateServlet extends HttpServlet {

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
	  //System.out.println("inside watchlist doPost");
    String resultwatchListId = req.getParameter("id");
   System.out.println(resultwatchListId);
   int watchListId = Integer.parseInt(resultwatchListId);
    HttpSession mySession = req.getSession(false);
    Users users = (Users) mySession.getAttribute("user");
    boolean updatedWatchList = false;

    WatchList watchList;

    // Retrieve watch list, and store as a message.
    try {
      watchList = watchListDao.getWatchListById(watchListId);
      if(watchList.getHasWatched()==1) {
    	  watchListDao.updateWatchList(watchList, 0);
      }
      else {
    	  watchListDao.updateWatchList(watchList, 1);
    	  }
      req.setAttribute("watchList", watchList);
      updatedWatchList = true;
      req.setAttribute("updatedWatchList", updatedWatchList);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }
    resp.getWriter().write(String.valueOf(updatedWatchList));
    req.getRequestDispatcher("/UserPage.jsp").forward(req, resp);
   
  }

}
