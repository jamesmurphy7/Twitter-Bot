package Main;
import Assets.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;

import Assets.Room;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TextAdventure {
	
	//TODO: handle tweeting duplicates
	public static void main(String args[]) throws TwitterException, InterruptedException, IOException, ParseException{
		TwitterAccount mytwitter = new TwitterAccount();
		//mytwitter.tweetPrompt("memes");
		//mytwitter.tweetprompt("hi hello sdf this is a simple test tweet i am just testing. to see if my twitter bot will handle the number of characters being more than 140 wich is higher than twitter allows and one other thing, im about to tweet that whole mess again here we go to see if my twitter bot will handle the number of characters being more than 140 wich is higher than twitter allows");
		//TimeUnit.SECONDS.sleep(45);
		//String pop = mytwitter.findpopularchoice(2);
		//System.out.println(pop);
		
		//Here is a bunch of code that tests the twitter functionality for player move -------------
		
		/*Game game = new Game();
		String[] dirQuestion = game.makeDirPrompt();
		int optionCount = mytwitter.tweetOption(dirQuestion);
		System.out.println(optionCount);
		TimeUnit.SECONDS.sleep(45);
		String dir = mytwitter.findpopularchoice(2);
		System.out.println(dir);
		game.movePlayer(dir);
		System.out.println(game.player.getCurrRoom().name);
		*/
		//----------------------------------------------------------------------------
		int a = 0;
		a = 5;
		Room newroom = new Room();
		newroom.parseRoomJSON("gamefiles/rooms/B3.json");
		
		Game game = new Game();

		game.startGame();
		
	}
}