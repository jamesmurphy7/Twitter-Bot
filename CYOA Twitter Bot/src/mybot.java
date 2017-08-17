package us.jamestalks.learning;

import java.util.List;
import java.util.concurrent.TimeUnit;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class mybot {
	
	public static int playgame(Book book1,Page curPage) throws TwitterException, InterruptedException{
		int end = 0;
		String searchString = "";
		int ID = 1;
		while(end == 0){
			int count = 0;
			Twitter twitter = TwitterFactory.getSingleton();
        
			List<Status> statuses = twitter.getUserTimeline();
			Status latest = statuses.get(0);
			
			mybot.tweetcurrentposition(twitter, curPage.prompt,curPage.Q1,curPage.Q2,curPage.Q3,curPage.Q4);
        
			TimeUnit.SECONDS.sleep(45);
       
			count = mybot.countoptions(curPage);
			Status finalchoice = mybot.findpopularchoice(twitter, count);
			
			searchString = finalchoice.getText().replace("@Play_By_Tweet ", "");
			
			System.out.println(searchString);
			
			ID = curPage.findID(searchString, curPage);
			System.out.println(ID);
			if(ID == 0){
				end = 1;
				return 1;
			}
			curPage = book1.book.get(ID);
			
			System.out.println(curPage.prompt);
		}	
		return 0;
	}
	
	public static void tweetcurrentposition(Twitter twitter, String prompt, String answr1, String answr2, String answr3, String answr4) throws TwitterException{
		//Twitter twitter = TwitterFactory.getSingleton();
	
		List<Status> statuses = twitter.getUserTimeline();
		Status latest = statuses.get(0);
    
		Status status = null;
		tweetprompt(twitter, status, prompt);
    
    
		if(answr1.equals("")){
			return;
		}
		statuses = twitter.getUserTimeline();
		latest = statuses.get(0);
    
		StatusUpdate statusUpdate = new StatusUpdate("@" + latest.getUser().getScreenName() + " " + answr1);
		statusUpdate.inReplyToStatusId(latest.getId());
		Status status2 = twitter.updateStatus(statusUpdate); 
    
		if(answr2.equals("")){
			return;
		}
		statuses = twitter.getUserTimeline();
		latest = statuses.get(0);
    
		statusUpdate = new StatusUpdate("@" + latest.getUser().getScreenName() +" " +answr2);
		statusUpdate.inReplyToStatusId(latest.getId());
		status2 = twitter.updateStatus(statusUpdate);
		
		if(answr3.equals("")){
			return;
		}
		statuses = twitter.getUserTimeline();
		latest = statuses.get(0);
		
		statusUpdate = new StatusUpdate("@" + latest.getUser().getScreenName() +" "+ answr3);
		statusUpdate.inReplyToStatusId(latest.getId());
    	status2 = twitter.updateStatus(statusUpdate); 
    
    	if(answr4.equals("")){
    		return;
    	}
    	statuses = twitter.getUserTimeline();
    	latest = statuses.get(0);
    
    	statusUpdate = new StatusUpdate("@" + latest.getUser().getScreenName() +" "+ answr4);
    	statusUpdate.inReplyToStatusId(latest.getId());
    	status2 = twitter.updateStatus(statusUpdate); 

	}

	public static void tweetprompt(Twitter twitter, Status status, String prompt) throws TwitterException{
		int tweetnum = 0;
		String[] words = prompt.split(" ");
		int size = words.length;
		String[] sentences = words;
		int j = 0;
		while(j<size){
			String tempsen = "";
			Boolean period = true;
			while(tempsen.length() <= 115 && j<size && period == true){
				period = words[j].charAt(words[j].length()-1)!='.';
				if(period == true){
					period = words[j].charAt(words[j].length()-1)!='?';
				}
				if(period == true){
					period = words[j].charAt(words[j].length()-1)!='!';
				}
				if(period == true){
					if(words[j].charAt(words[j].length()-1)=='"'){
						period = words[j].charAt(words[j].length()-2)!='.';
					}
				}
				tempsen = tempsen + words[j] + " ";
				j++;
			}
			sentences[tweetnum]= tempsen;
			tweetnum++;
		}
		
		//System.out.print("len of string: ");
		//System.out.print(words[0].length());
		status = twitter.updateStatus(words[0]);
		for(int i = 1;i<tweetnum;i++){
			List<Status> statuses = twitter.getUserTimeline();
		    Status latest = statuses.get(0);
		    
		    StatusUpdate statusUpdate = new StatusUpdate("@Play_By_Tweet"+ " " + sentences[i]);
		    statusUpdate.inReplyToStatusId(latest.getId());
		    //System.out.print("len of string: ");
			//System.out.print(statusUpdate.getStatus().length());
		    Status status2 = twitter.updateStatus(statusUpdate); 
		    
		}
	}

	public static int countoptions(Page page){
		int num = 0;
		if(!page.Q1.equals("")){
			num++;
		}
		if(!page.Q2.equals("")){
			num++;
		}
		if(!page.Q3.equals("")){
			num++;
		}
		if(!page.Q4.equals("")){
			num++;
		}
		return num;
	}
	
	public static Status findpopularchoice(Twitter twitter, int count) throws TwitterException{
		
		int maxlikes = 0;
		int templikes = 0;
		Status ChosenStatus;
		List<Status> statuses = twitter.getUserTimeline();
		Status[] status = new Status[4];
		ChosenStatus = statuses.get(0);
		for(int i=0;i<count;i++){
			status[i] = statuses.get(i);
			templikes = status[i].getFavoriteCount();
			if(templikes>maxlikes){
				maxlikes = templikes;
				ChosenStatus = status[i];
			}
		}
		return ChosenStatus;
	}
}
