package app.tools;
import java.sql.SQLException;
import java.util.List;

import app.dal.PlatformsDao;
import app.dal.RecommendationDao;
import app.dal.StreamingPlatformDao;
import app.dal.UsersDao;
import app.dal.WatchListDao;
import app.model.Platforms;
import app.model.Recommendation;
import app.model.StreamingPlatform;
import app.model.Users;
import app.model.WatchList;
public class Inserter {
  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    RecommendationDao recommendationDao = RecommendationDao.getInstance();
    UsersDao usersDao = UsersDao.getInstance();
    WatchListDao watchListDao =WatchListDao.getInstance();
    PlatformsDao platformsDao = PlatformsDao.getInstance();
    StreamingPlatformDao streamingPlatformDao =StreamingPlatformDao.getInstance();
     
    Recommendation recommendation1 = new Recommendation("re001", "Coffee & Kareem", 2020, "Action,Comedy", 88,"US", "English");
    recommendation1 = recommendationDao.create(recommendation1);
    
    Recommendation recommendation2 = new Recommendation("re002", "Avatar", 2008, "Science Fiction", 120,"US", "English");
    recommendation2 = recommendationDao.create(recommendation2);
    
    Recommendation recommendation3 = new Recommendation("re003", "Frozen", 2018, "Animation", 120,"US", "English");
    
    recommendation3 = recommendationDao.create(recommendation3);
    
    Recommendation recommendation4 = new Recommendation("re004", "Bulletproof 2", 2020, "Action,Comedy", 97,"US", "English");
    
    recommendation4= recommendationDao.create(recommendation4);
    
    Recommendation re002 = recommendationDao.getRecommendationByRecommendationId("re002");
    
    System.out.format("id: %s title: %s year: %s genre: %s length: %s country: %s language: %s\n",
            re002.getRecommendationId(), re002.getTitle(), re002.getYear(), re002.getGenre(),
            re002.getRuntimeMinutes(), re002.getCountry(), re002.getLanguage());
    recommendationDao.updateLanguage(re002, "French");
    
    re002 = recommendationDao.getRecommendationByRecommendationId("re002");
    System.out.format("id: %s title: %s year: %s genre: %s length: %s country: %s language: %s\n",
            re002.getRecommendationId(), re002.getTitle(), re002.getYear(), re002.getGenre(),
            re002.getRuntimeMinutes(), re002.getCountry(), re002.getLanguage());
    Recommendation re003 = recommendationDao.getRecommendationByRecommendationId("re003");
    System.out.format("id: %s title: %s year: %s genre: %s length: %s country: %s language: %s\n",
            re003.getRecommendationId(), re003.getTitle(), re003.getYear(), re003.getGenre(),
            re003.getRuntimeMinutes(), re003.getCountry(), re003.getLanguage());
    recommendationDao.delete(re003);
    
    
    //Create user
    Users user1 = new Users("hotpotmoth","hotpot1","hotpotmoth1@yahoo.com");
    user1 = usersDao.create(user1);
    
    Users user2 = new Users("Boulder222","123456","brian_boulder@gmail.com");
    user2 = usersDao.create(user2);
    
    Users user3 = new Users("ClaireBear","claire1","claire_b@gmail.com");
    user3 = usersDao.create(user3);
    	    
    Users user4 = new Users("babymoth","moth2","mothman@hotmail.com");
    user4 = usersDao.create(user4);
    
    Users user5 = new Users("falling_star","fs4","fallingstar1@yahoo.com");
    user5 = usersDao.create(user5);
    
    
    //Read User
    
 	Users u1 = usersDao.getUserByUserName("Boulder222");
 	System.out.format("Reading user: username : %s password : %s email : %s \n",
 			u1.getUserName(), u1.getPassword(), u1.getEmail());
    	    
 	//Update password of the user
 		
 	 String newPassword = "NewPassword";
 	 user3 = usersDao.updatePassword(user3, newPassword);
 	 user3 = usersDao.getUserByUserName(user3.getUserName());
 	if(user3.getPassword().equals(newPassword))
	{
		System.out.println("Updated the password of the user Successfully");
	}
	else
	{
		System.out.println("Failed to update the new password for the user");
	}
// 	
 	//Delete user
 	
 	user4 = usersDao.delete(user4);
 	if(user4 ==null)
	{
		System.out.println("Deleted user Successfully");
	}
	else
	{
		System.out.println("Failed to delete the user");
	}
 	
 	//Insert  watch list for user  Boulder222
 	
 	WatchList watchList1 = new WatchList(user2, recommendation1,1);
 	watchList1 = watchListDao.create(watchList1);
 	
 	WatchList watchList2 = new WatchList(user2, recommendation2,0);
 	watchList2 = watchListDao.create(watchList2);
 	
 	WatchList watchList3 = new WatchList(user1, recommendation2,0);
 	watchList3= watchListDao.create(watchList3);
 	
 	// Reading watch list
 	List<WatchList> w1 = watchListDao.getWatchListByUserName("Boulder222");
 	for(WatchList rs : w1) {
		System.out.format("Looping watchlist: watchlistid:%s UserName:%s RecommendationId:%s RecommendationName : %s HasWatched:%s \n",
				rs.getWatchListId(),rs.getUser().getUserName(),rs.getRecommendation().getRecommendationId(),
				rs.getRecommendation().getTitle(), rs.getHasWatched());
	}
 	
 	//update haswatched in the watchlist
 	int oldValue = watchList2.getHasWatched();
 	watchList2 = watchListDao.updateWatchList(watchList2, 1);
 	int newValue = watchList2.getHasWatched();
 	if(oldValue!=newValue) {
 		System.out.println("Updated hasWatched Successfully");
 	}
 	else
 	{
 		System.out.println("Failed to update hasWatched in the watchlist");
 	}
 	
 	//Delete Watchlist
 	watchList3 = watchListDao.delete(watchList3);
 	if(watchList3 ==null)
	{
		System.out.println("Deleted watchlist Successfully");
	}
	else
	{
		System.out.println("Failed to delete the watchlist");
	}
 	
 	
 	//insert in Platforms
 	
 	Platforms platform1 = new Platforms("Netflix");
 	platform1 = platformsDao.create(platform1);
 	Platforms platform2 = new Platforms("HULU");
 	platform2 = platformsDao.create(platform2);
 	Platforms platform3 = new Platforms("PRIMEVIDEO");
 	platform3 = platformsDao.create(platform3);
 	Platforms platform4 = new Platforms("DISNEY+");
 	platform4 = platformsDao.create(platform4);
 	
 	
 	//Insert Streaming Platforms
 	StreamingPlatform sp1 = new StreamingPlatform(recommendation1, platform1);
 	sp1 = streamingPlatformDao.create(sp1);
 	
 	StreamingPlatform sp2 = new StreamingPlatform(recommendation2, platform4);
 	sp2 = streamingPlatformDao.create(sp2);
 	
	StreamingPlatform sp3 = new StreamingPlatform(recommendation4, platform1);
 	sp3 = streamingPlatformDao.create(sp3);
 	
 	
 	
 	Users newValid = usersDao.checkLogin("apekshaagarwal.20@gmail.com", "apeksha1234");
 	if(newValid != null){
 		System.out.println("Successfully got the user");
 	}

  }
  
}
