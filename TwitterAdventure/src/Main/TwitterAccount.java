package Main;
import Assets.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterAccount {
	
	private Twitter twitter = null;
	
	TwitterAccount(){
		twitter = TwitterFactory.getSingleton();
	}
	
	
	//this function returns a string that is the text of the tweet that
	//recived the most likes from the most recent thread
	//Parameter: the amount of replies to check
	public String findpopularchoice(int count) throws TwitterException{
		int maxlikes = 0;
		int templikes = 0;
		Status ChosenStatus;
		List<Status> statuses = twitter.getUserTimeline();
		Status[] status = new Status[100];
		ChosenStatus = statuses.get(0);
		for(int i=0;i<count;i++){
			status[i] = statuses.get(i);
			templikes = status[i].getFavoriteCount();
			if(templikes>maxlikes){
				maxlikes = templikes;
				ChosenStatus = status[i];
			}
		}
		return ChosenStatus.getText().replaceFirst("@Play_By_Tweet ","");
	} 

	
	//tweets a long string by replies
	//a string with mutiple spaces in a row breaks this function
	//if the final word is very long this function breaks
	public void tweetPrompt(String prompt) throws TwitterException{
		int tweetnum = 0;
		String[] words = prompt.split(" ");
		int size = words.length;
		String[] sentences = words;
		int j = 0;
		int debugint = 0;
		while(j<size){
			String tempsen = "";
			Boolean period = true;
			while(j<size && period == true){
				//multiple space breakes here
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
				debugint = tempsen.length();
				try {
					if(tempsen.length() + words[j+1].length()>=120 ) {
						break;
					}
				}
				//if the final word is very long this function breaks around here
				catch(ArrayIndexOutOfBoundsException exception) {
				    //do nothing
				}	
			}
			sentences[tweetnum]= tempsen;
			tweetnum++;
		}
		//System.out.print("len of string: ");
		//System.out.print(words[0].length());
		twitter.updateStatus(sentences[0]); 
		for(int i = 1;i<tweetnum;i++){
			List<Status> statuses = twitter.getUserTimeline();
		    Status latest = statuses.get(0);
		    
		    StatusUpdate statusUpdate = new StatusUpdate("@Play_By_Tweet"+ " " + sentences[i]);
		    statusUpdate.inReplyToStatusId(latest.getId());
		    //System.out.print("len of string: ");
			//System.out.print(statusUpdate.getStatus().length());
		    twitter.updateStatus(statusUpdate); 
		}	
		return;
	}
	//tweets a series of strings in reply to each other
	//assumes each string after index 0 is not too long to tweet
	public int tweetOption(String[] prompts) throws TwitterException {
		tweetPrompt(prompts[0]);
		int optionsCounted = 0;
		for(int i = 1;i< prompts.length;i++) {
			List<Status> statuses = twitter.getUserTimeline();
		    Status latest = statuses.get(0);
		    StatusUpdate statusUpdate = new StatusUpdate("@Play_By_Tweet"+ " " + prompts[i]);
		    statusUpdate.inReplyToStatusId(latest.getId());
		    twitter.updateStatus(statusUpdate);
		    optionsCounted++;
		}
		return optionsCounted;
	}
	
	
	
}
