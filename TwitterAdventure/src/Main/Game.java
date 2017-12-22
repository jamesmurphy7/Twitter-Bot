package Main;
import Assets.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Game {
	//intance 0: game is at the starting stage and all is normal
	//intance -1: game has ended, run ending game logic
	int instance;
	Map gamemap;
	Player player;
	Room currRoom;
	final List<String> basicPrompt = Arrays.asList("What would you like to do?","look","move","examine","use item");
	public final static String moveQuestion = "Which way would you like to move";
	/*
	* Game
	* constructor for a game object
	* calls makeMap and readMap and link rooms to make the gamemap and instance the rooms
	* calls makePlayer to instance the player with currRoom at correct room 
	*/
	Game() {
		instance = 0;
		//make map bassically works as a constructor making a basic map
		gamemap = makeMap();
		//these two functions add and link all the rooms
		gamemap.readmap("gamefiles/map.txt");
		gamemap.linkRooms();
		player = makePlayer();
		currRoom = player.getCurrRoom();
	}

	/*
	* makeMap
	* Makes the map of the game by reading inputfile gamefiles/mapnotes 
	* only sets up the map to be read from file by allocating space for it in the array
	*/
	public Map makeMap()  {
		File inputfile = new File("gamefiles/mapnotes.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(inputfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String mapsizeline = sc.nextLine();
		String[] xys = mapsizeline.split(" ");
		int x = Integer.parseInt(xys[1]);
		int y = Integer.parseInt(xys[0]);
		Map newmap = new Map(y,x);
		return newmap;
	}
	/*
	* makePlayer
	* makes the player loc by setting it to the loc from the file
	* file is gamefilees/playernotes containing a y x coordinate of the starting room
	* returns: a newly constructed player with the curr room made from file loc
	*/
	public Player makePlayer()  {
		File inputfile = new File("gamefiles/playernotes.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(inputfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String playerlocline = sc.nextLine();
		String[] xys = playerlocline.split(" ");
		int x = Integer.parseInt(xys[1]);
		int y = Integer.parseInt(xys[0]);
		Room currRoom = gamemap.getRoomAt(y,x);
		Player newplayer = new Player(currRoom);
		//make inventory
		Room tempR = null;
		tempR = new Room("../inventory");
		newplayer.setInventory(tempR.getItems());
		
		//return newplayer
		return newplayer;
	}
	
	/*
	 * prints a prompt and then scans for the answer returning the string that is the answer
	 */
	public static String printPrompt(Scanner sc, List<String> prompt) {
		if(prompt == null) {
			return "";
		}
		String returnString = "";
		for(String line : prompt) {
			System.out.println(line);
		}
		returnString = sc.nextLine();
		return returnString;
	}
	
	/*
	 * overload with 2 string lists
	 * the first string list is ment to be and into like a look list
	 * the second string list is ment to be the options list
	 */
	public static String printPrompt(Scanner sc, List<String> startingPrompt, List<String> endingPrompt) {
		String returnString = "";
		if(startingPrompt == null || endingPrompt == null) {
			return "";
		}
		for(String line : startingPrompt) {
			System.out.println(line);
		}
		System.out.println();
		for(String line : endingPrompt) {
			System.out.println(line);
		}
		returnString = sc.nextLine();
		return returnString;
	}
	
	
	
	/*
	 * StartGame
	 *runs basic game logic loop
	 */
	public void startGame() {
		Scanner sc = new Scanner(System.in);
		boolean gameEnd = false;
		String userInputLine = "";
		
		userInputLine = printPrompt(sc,basicPrompt);
		
		while(!gameEnd) {
			if(userInputLine.equals("look")) {
				userInputLine = printPrompt(sc, player.getCurrRoom().makeLookStrings(), basicPrompt);
			}
			else if(userInputLine.contains("420") || userInputLine.contains("69")) {
				System.out.println();
				System.out.println("NICE.");
				System.out.println();
				userInputLine = printPrompt(sc,basicPrompt);
			}
			else if(userInputLine.equals("move")) {
				userInputLine = printPrompt(sc,player.move(sc),basicPrompt);
			}
			else if(userInputLine.equals("use item")) {
				userInputLine = printPrompt(sc,player.useItem(sc),basicPrompt);
			}
			else if(userInputLine.equals("")) {
				userInputLine = printPrompt(sc,basicPrompt);
			}
			else {
				System.out.println("invalid command");
				userInputLine = printPrompt(sc,basicPrompt);
			}
		}
		//place clean up here
	}
	
	//end of class functions-------------------------------------------------------------------
}
