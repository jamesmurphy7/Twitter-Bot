package Assets;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;



public class Room {
	final String RoomRelativeFilePath = "gamefiles/rooms/";
	final String ItemRelativeFilePath = "gamefiles/items/";
	private String name;
	private String description;
	private HashMap<String,Item> inventory;
	private HashMap<String,String> flags;
	//links to other rooms
	private Room northRoom;
	private Room southRoom;
	private Room eastRoom;
	private Room westRoom;
	/*
	 * Room
	 * here are the constructors for room class
	 */
	//Constructors-----------------------------------------------------------------------------------------------------
	public Room(){
		this.name = "";
		this.description = "";
		this.northRoom = null;
		this.southRoom = null;
		this.eastRoom = null;
		this.westRoom = null;
		this.flags = new HashMap<String,String>();
		this.inventory = new HashMap<String,Item>();
	}
	//constructor with a string input as the file name 
	public Room(String n) {
		String fileloc = RoomRelativeFilePath + n + ".json";
		this.name = n;
		this.description = "";
		this.northRoom = null;
		this.southRoom = null;
		this.eastRoom = null;
		this.westRoom = null;
		this.flags = new HashMap<String,String>();
		this.inventory = new HashMap<String,Item>();
		this.parseRoomJSON(fileloc);
	}
	//Getters-----------------------------------------------------------------------------------------------------------
	public String getName() {
		return this.name;
	}
	public Room getWest() {
		return this.westRoom;
	}
	public Room getEast() {
		return this.eastRoom;
	}
	public Room getNorth() {
		return this.northRoom;
	}
	public Room getSouth() {
		return this.southRoom;
	}
	public HashMap<String,String> getFlags(){
		return this.flags;
	}
	public String getDes() {
		return this.description;
	}
	public HashMap<String,Item> getItems(){
		return this.inventory;
	}
	//Setters----------------------------------------------------------------------------------------------------------
	public void setWest(Room r) {
		this.westRoom = r;
	}
	public void setEast(Room r) {
		this.eastRoom = r;
	}
	public void setNorth(Room r) {
		this.northRoom = r;
	}
	public void setSouth(Room r) {
		this.southRoom = r;
	}
	public void setName(String s) {
		this.name = s;
	}
	public void setDes(String d) {
		this.description = d;
	}
	//Class functions-------------------------------------------------------------------------------------------
	//TODO: implement JSON file implementation for the construction of a room object
	/*
	 * parseRoomJson
	 * reads the filename as a json and fills the room variables 
	 * constructs Item objects in the inventory hashmap
	 * filename: the input filename of the json file
	 */
	public void parseRoomJSON(String filename) {
		Object object = null;
		try {
			FileReader r = new FileReader(filename);
			try {
				object =  new JSONParser().parse(new FileReader(filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		catch (FileNotFoundException e) {
			//the file is not in the folder
			System.err.println(filename + " room not read");
			return;
		}
		Room newRoom = new Room();
		JSONObject json = (JSONObject) object;
		name = (String) json.get("name");
		description = (String) json.get("description");
		//steps for parsing inventory hashmap
		//1. change JSON file to be String:String for all inventory where its "name":"filename"
		//2.store those two things a strings
		//3. uses those strings to construct item objects for a hashmap
		//4. make the hashmap and add each one to it
		HashMap<String,String> map = new HashMap<String,String>();
		try {
			map = ((HashMap)json.get("inventory"));
		}
		catch (Throwable e) {
			System.err.println("hash map of Items not read correctly");
		}
		//get the array of flags
		
		try {		
			flags =  (HashMap<String, String>) json.get("flags");
			}
			catch (Throwable e) {
				System.err.println("Hashmap of flags not read correctly");
			}
		//get and construct all of the items in the json map
		//adds flags if locked door is in the file
		for(Map.Entry<String,String> entry : map.entrySet()) {
			String tempItemName = entry.getKey();
			String itemFileLoc = ItemRelativeFilePath + tempItemName + ".json";
			Item tempItem = new Item();
			try {
				tempItem.parseItemJson(itemFileLoc);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inventory.put(tempItemName,tempItem);
			String flagname = entry.getValue();
			this.addFlagName(flagname);
		}
		
		//function finished
	}
	/*
	 * param: the string of the flag
	 * adds a flag if the flag name indicates a locked door
	 */
	private void addFlagName(String flagname) {
		// TODO Auto-generated method stub
		if(flagname.equals("northLock")) {
			this.getFlags().put("northLock","north door is locked");
		}
		if(flagname.equals("eastLock")) {
			this.getFlags().put("eastLock","east door is locked");
		}
		if(flagname.equals("southLock")) {
			this.getFlags().put("southLock","south door is locked");
		}
		if(flagname.equals("westLock")) {
			this.getFlags().put("westLock","west door is locked");
		}
	}
	/*
	 * removeFlag
	 * removes hash map item from flags map
	 */
	public boolean removeFlag(String flagName) {
		if(this.flags.remove(flagName) == null) {
			//System.out.println("failed");
			return false;
		}
		//System.out.println("remove succ");
		return true;
	}
	
	public List<String> makeLookStrings() {
		List<String> lookString = new ArrayList<String>();
		lookString.add(this.description);
		if(this.northRoom != null) {
			lookString.add("there is a door to the north");
		}
		if(this.eastRoom != null) {
			lookString.add("there is a door to the east");
		}
		if(this.southRoom != null) {
			lookString.add("there is a door to the south");
		}
		if(this.westRoom != null) {
			lookString.add("there is a door to the west");
		}
		for(Item item : this.inventory.values()) {
			if(!item.getLoc().equals("none")) {
				lookString.add("you find a " + item.getName() + " lying in the middle of the room.");
			}
			else {
				lookString.add("you find a " + item.getName() + item.getLoc());
			}
		}
		return lookString;
	}
//end of class	
}
