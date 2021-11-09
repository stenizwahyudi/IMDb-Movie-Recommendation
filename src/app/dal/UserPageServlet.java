package app.dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.model.UserSubscriptions;
import app.model.Users;
import app.model.WatchList;


@WebServlet("/userpage")
public class UserPageServlet extends HttpServlet {
	
	protected WatchListDao watchListDao;
	protected UserSubscriptionsDao userSubscriptionsDao;
	
	@Override
	public void init() throws ServletException {
		watchListDao = WatchListDao.getInstance();
		userSubscriptionsDao = UserSubscriptionsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String recommendationId = req.getParameter("id");
        HttpSession mySession = req.getSession(true);
        Users users = (Users) mySession.getAttribute("user");
		// Retrieve and validate UserName.
        String userName = users.getUserName();
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("title", "Invalid username.");
        } else {
        	messages.put("title", "Page for " + userName);
        }
        
        // Retrieve Users, and store in the request.
        List<WatchList> watchLists = new ArrayList<WatchList>();
        try {
        	watchLists = watchListDao.getWatchListByUserName(userName);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("watchLists", watchLists);
        List<UserSubscriptions> userSubscriptions = new ArrayList<UserSubscriptions>();
        try {
        	userSubscriptions = userSubscriptionsDao.getUserSubscriptionByUserName(userName);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("userSubscriptions", userSubscriptions);
        req.getRequestDispatcher("/UserPage.jsp").forward(req, resp);
	}
}
