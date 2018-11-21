package christmas2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

class Tweeter extends Thread {
	
	private final String OAuthConsumerKey = "M68k5vA3gwnXQ4myzBihvFvWc";
	private final String OAuthConsumerSecret = "bSuH8Pbb97r2FslVkOD7XKWtnx1cnc430uwHTrYPHGAIMvvuXz";
	private final String OAuthAccessToken = "929901786982268928-GZNutaQ2aNbpzpPFu5IibiLtDO8mnfO";
	private final String AccessTokenSecret = "4LKOFgr5UloirQOm3CL1ySiw6nf71ErMaEvumMnwBzxaO";
	private HashMap<String,String> usedTweets;
	private List<String> adjectivesList;
	private List<String> nounsList;
	
	String capital(String string) {
		return string.substring(1,2).toUpperCase() + string.substring(1);
	}
	
	@Override
	public void run() {
		
		usedTweets = readUsedTweets("used.txt");
		adjectivesList = readAdjectives("Adjectives.txt");
		nounsList = readVerbs("nouns.txt");
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	            .setOAuthConsumerKey(OAuthConsumerKey)
	            .setOAuthConsumerSecret(OAuthConsumerSecret)
	            .setOAuthAccessToken(OAuthAccessToken)
	            .setOAuthAccessTokenSecret(AccessTokenSecret);
	    
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    Twitter twitter = tf.getInstance();
	    
	    String tweet = "hello again I'm Back";
	    //StatusUpdate status = new StatusUpdate(tweet);
	    /*try {
			twitter.updateStatus(status);
		} 
	    catch (TwitterException e1) {
	    	e1.printStackTrace();
	    }*/
	   
	    
	    System.out.println("done.");
		
	}
	
	private ArrayList<String> readAdjectives(String filename) {
		ArrayList<String> adjectivesList = new ArrayList<String>();
		File adjectivesFile = new File(filename);
		Scanner sc = null;
		try {
			sc = new Scanner(adjectivesFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			adjectivesList.add(line);
		}
		return adjectivesList;
	}
	
	private ArrayList<String> readVerbs(String filename) {
		ArrayList<String> verbsList = new ArrayList<String>();
		File verbsFile = new File(filename);
		Scanner sc = null;
		try {
			sc = new Scanner(verbsFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			verbsList.add(line);
		}
		return verbsList;
	}
	
	private HashMap<String,String> readUsedTweets(String filename) {
		HashMap<String,String> usedMap = new HashMap<String,String>();
		File usedFile = new File(filename);
		Scanner sc = null;
		try {
			sc = new Scanner(usedFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			usedMap.put(line,line);
		}
		return usedMap;
	}
	
	private static void collectOldTweets(Twitter twitter, String handleName) throws TwitterException  {
		
		Paging paging = new Paging();
		paging.setCount(200);
		
		
		
		//List<Status> statuses = twitter.getUserTimeline();
		List<Status> statuses = twitter.getUserTimeline(handleName,paging);
		
		BufferedWriter out = null;
		try {
			out = new BufferedWriter( 
	                   new FileWriter("used.txt", true)); 
			//writer2 = new PrintWriter("dotFile" + fileNum + ".png");
		} catch (FileNotFoundException e ) {
			e.printStackTrace();
		}
		catch (IOException e2){
			e2.printStackTrace();
		}
		for(Status status : statuses) {
			try {
				out.write(status.getText() + "\n");
			}
			catch (IOException e2){
				e2.printStackTrace();
			}
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int sendTweet(Twitter twitter, String message) {
		StatusUpdate status = new StatusUpdate(message);
	    try {
			twitter.updateStatus(status);
			if(!usedTweets.containsKey(message)) {
				usedTweets.put(message,message);
				//write to usedFile---------------------------
				BufferedWriter out = null;
				try {
					out = new BufferedWriter( 
			                   new FileWriter("used.txt", true)); 
				} catch (FileNotFoundException e ) {
					e.printStackTrace();
				}
				catch (IOException e2){
					e2.printStackTrace();
				}
				try {
					out.write(message + "\n");
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//done writing to used file-------------------------
			}
			return 0;
		} 
	    catch (TwitterException e1) {
	    	return 1;
	    }
	}
	
	private class TextGenerator {
		int mode;
		int adjCount;
		int nounCount;
		
		public TextGenerator() {
			int mode = 1;
			int adjCount = 217;
			int nounCount = 1;
		}
		
		public String generateVeryMsg() {
			
			return "";
		}
		
	}
	
}//end of Tweeter class
