package us.jamestalks.learning;
import java.util.concurrent.TimeUnit;
import java.util.List;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
public class Page {

	String prompt;
	int Pid;
	String Q1;
	int id1;
	String Q2;
	int id2;
	String Q3;
	int id3;
	String Q4;
	int id4;
	
//default constructor for a page	
	public Page(){
		prompt = "";
		Pid = 0;
		Q1 = "";
		Q2 = "";
		Q3 = "";
		Q4 = "";
		id1 = 0;
		id2 = 0;
		id3 = 0;
		id4 = 0;
	}
//gets the ID of a string in a page, returns -1 if string cannot be found in the page
	public int findID(String Q, Page page){
		if(Q.equals(Q1)){
			return page.id1;
		}
		else if(Q.equals(Q2)){
			return page.id2;
		}
		else if(Q.equals(Q3)){
			return page.id3;
		}
		else if(Q.equals(Q4)){
			return page.id4;
		}
		else{
			return -1;
		}
	}
	
	public static Page findPagebyID(){
		return null;
	}
}
