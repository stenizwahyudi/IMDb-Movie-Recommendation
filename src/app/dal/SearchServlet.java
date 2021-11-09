package app.dal;

import java.io.IOException;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.model.Platforms;
import app.model.Recommendation;
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
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

  protected RecommendationDao recommendationDao;
  protected PlatformsDao platformsDao;

  @Override
  public void init() throws ServletException {
    recommendationDao = RecommendationDao.getInstance();
    platformsDao = PlatformsDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    String recommendationId = req.getParameter("id");
    HttpSession mySession = req.getSession(false);
    Users users = (Users) mySession.getAttribute("user");
    List<Recommendation> recommendations;
    Map<Recommendation, List<Platforms>> map = new  HashMap<>();

    String title = req.getParameter("title");
    if (title == null || title.trim().isEmpty()) {
      title = "";
    }
    String genres = req.getParameter("genres");
    if (genres == null || genres.trim().isEmpty()) {
      genres = "";
    }
    String language = req.getParameter("language");
    if (language == null || language.trim().isEmpty()) {
      language = "";
    }
    String year = req.getParameter("year");
    
    if (year == null || year.trim().isEmpty()) {
      year = "0";
    }
    
    int newYear = Integer.parseInt(year);

    try {
      recommendations = recommendationDao.getRecommendationByRecommendationInfo(title, genres, language, newYear);
      for(Recommendation recommendation: recommendations){
        List<Platforms> platforms = platformsDao.getPlatformsForRecommendation(recommendation);
        map.put(recommendation, platforms);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }

    req.setAttribute("recommendations", map);

    req.getRequestDispatcher("/Search.jsp").forward(req, resp);
  }

}
