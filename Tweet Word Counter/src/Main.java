
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public class Main {

	public static void main(String[] args) throws TwitterException, InterruptedException{
		
		Twitter twitter = TwitterFactory.getSingleton();
		List<Status> statuses = twitter.getUserTimeline();
		List<String> badStrings = new ArrayList<String>();
		//add list of bad strings here-----------------------------------------------------------------
		badStrings.add("");
		badStrings.add("the");
		badStrings.add("to");
		badStrings.add("and");
		badStrings.add("in");
		badStrings.add("a");
		badStrings.add("is");
		badStrings.add("of");
		badStrings.add("for");
		badStrings.add("are");
		badStrings.add("on");
		badStrings.add("will");
		badStrings.add("with");
		badStrings.add("at");
		badStrings.add("our");
		badStrings.add("has");
		badStrings.add("was");
		badStrings.add("it");
		badStrings.add("that");
		badStrings.add("have");
		badStrings.add("as");
		badStrings.add("we");
		badStrings.add("The");
		badStrings.add("be");
		badStrings.add("not");
		badStrings.add("you");
		badStrings.add("my");
		badStrings.add("just");
		badStrings.add("about");
		badStrings.add("from");
		//----------------------------------------------------------------------------------------------
		//twitter.updateStatus("test test");
		//Word w = new Word();
		HashMap<String,Word> WordMap;
		WordMap = new HashMap<String,Word>();
		//get the user inputs-------------------------------------------------------------
		System.out.println("enter the name of the acount you want to parse:");
		Scanner scanner = new Scanner(System.in);
		String twitterhandle = scanner.nextLine();

		if (twitterhandle == "0") {
			twitterhandle = "@realDonaldTrump";
		}
		System.out.println("enter the amount of tweets you would like to count");
		String numtweetsS = scanner.nextLine();
		int numtweets = Integer.parseInt(numtweetsS);
		Paging paging = new Paging();
		paging.setCount(numtweets);
		scanner.close();
		//start reading the tweets--------------------------------------------------------
		statuses = twitter.getUserTimeline(twitterhandle,paging);
		//for each tweet given
		for(int i = 0; i<numtweets; i++) {
			Status status1 = statuses.get(i);
			String tweetstring = status1.getText();
			if(!(tweetstring.substring(0, 2).equals("RT"))) {
				//System.out.println(tweetstring);
				tweetstring.replaceAll("-", " ");
				String[] words = tweetstring.split(" ");
				for (int j = 0; j < words.length;j++) {
					String tempword = words[j].replaceAll("[^A-Za-z]+", "");
					//if the word is in the bad word list
					if(badStrings.contains(tempword)) {
						//do nothing
					}
					//if the word is @ an account
					else if (tempword.substring(0,1).equals("@")) {
						//do nothing
					}
					//if the word is in the map already
					else if (WordMap.containsKey(tempword)) {
						WordMap.get(tempword).increment();
					}
					else {
						Word word = new Word();
						word.word = tempword;
						word.count = 1;
						WordMap.put(tempword, word);
					}
				}
			}		
		}
		//sort the list---------------------------------------------------------------------------------------
		System.out.println("program has finished reading tweets");
		int h = 0;
		List<Word> WordList = new ArrayList<Word>(WordMap.values());
		
		Collections.sort(WordList, new Comparator<Word>() {

	        public int compare(Word o1, Word o2) {
	        	return o1.get_count() < o2.get_count() ? 1
	        	         : o1.get_count() > o2.get_count() ? -1
	        	         : 0;
	        }
	    });
		
		System.out.println("program finished sorting tweets");
		//----------------------------------------------------------------------------------------------------
		try{
		    PrintWriter writer = new PrintWriter("C:\\\\Users\\\\james\\\\Desktop\\\\Tweet Counts.txt", "UTF-8");
		    writer.println("[word]\t\t[count]\n");
		    writer.println("\n");
		    for(int k = 0;k<WordList.size();k++) {
		    	if(WordList.get(k).word.length()>=8) {
		    		writer.println(WordList.get(k).word+"\t"+WordList.get(k).count);
		    	}
		    	else {
		    		writer.println(WordList.get(k).word+"\t\t"+WordList.get(k).count);	
		    	}
		    }
		    writer.close();
		} catch (IOException e) {
		   // do something
			System.out.println("file could not be opend");
		}
		System.out.println("program has finished");
	}
}
