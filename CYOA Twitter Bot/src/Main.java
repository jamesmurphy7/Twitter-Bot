package us.jamestalks.learning;

import java.util.List;
import java.util.Scanner;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Main{

    //if something goes wrong, we might see a TwitterException
	public static void main(String[] args) throws TwitterException, InterruptedException{
		//read for user command 
		Scanner reader = new Scanner(System.in);  
		System.out.println("Type \"1\" to run book Type \"0\" to delete all tweets");
		int n = reader.nextInt();
//------//create the book and get the first page==================================================
		Book book1 = new Book();
		book1.constructMap();
    	Page curPage = book1.book.get(1);
//================================================================================================
//play game --------------------------------------------------------------------------------------
    	if(n==1){	
    		mybot.playgame(book1, curPage);
		}
//delete tweets===================================================================================
    	if(n==0){
    		Twitter twitter = TwitterFactory.getSingleton();
            for (int j = 0;j<2000;j++){
			List<Status> statuses = twitter.getUserTimeline();
			Status latest = statuses.get(0);
			long idnum= latest.getId();
			twitter.destroyStatus(idnum);
            }
            System.out.println("all tweets deleted\n");
    	}
//================================================================================================    	
    }

	
}
