package Assets;
import java.util.*;
import Main.*;

public class Player {
	private Room currRoom;
	HashMap<String,Item> inventory;
	
	public Player(Room r){
		this.currRoom = r;
		inventory = new HashMap<String,Item>();
	}
	//settter for currRoom
	public void setCurrRoom(Room r) {
		this.currRoom = r;
	}
	public void setInventory(HashMap<String,Item> i) {
		this.inventory = i;
	}
	public Room getCurrRoom() {
		return this.currRoom;
	}
	public HashMap<String,Item> getInventory(){
		return this.inventory;
	}
	/*
	 * useItem
	 * runs all the if statements for items
	 * return flags:
	 * 1: print out the new room look
	 */
	public List<String> useItem(Scanner sc) {
		List<String> possibleItems = new ArrayList<String>();
		
		boolean noitems = true;
		possibleItems.add("which item would you like to use?");
		
		for(Item item : this.getInventory().values()) {
			noitems = false;
			possibleItems.add(item.getName());
		}
		if(noitems == true) {
			possibleItems.remove("which item would you like to use?");
			possibleItems.add("there are no items you can use right now");
			return possibleItems;
		}
		//get item choice here
		String itemchoice = Game.printPrompt(sc,possibleItems);
		//run item action here
		Item item = null;
		List<String> itemAction = new ArrayList<String>();
		//get the item choosen
		if(this.inventory.containsKey(itemchoice)) {
			item = this.inventory.get(itemchoice);
		}
		else if(this.getCurrRoom().getItems().containsKey(itemchoice)) {
			item = this.getCurrRoom().getItems().get(itemchoice);
		}
		else {
			System.err.println("item choice could not be found");
			System.exit(1);
		}
		//use the item choosen if its just a basic item funtion
		if(!item.getChain()) {
			if(currRoom.getFlags().containsKey(item.getFlag())) {
				itemAction.add(item.getAction());
				this.currRoom.removeFlag(item.getFlag());
				return itemAction;
			}
			else {
				itemAction.add(item.getName() + " did not do anything");
				return itemAction;
			}
		}
		//use the item if it is used on something
		else {
			List<String> targetsList = new ArrayList<String>();
			boolean notargets = true;
			targetsList.add("what would you like to use this on?");
			for(Item target : this.getCurrRoom().getItems().values()) {
				if(target.getType() == 1) {
					notargets = false;
					targetsList.add(target.getName());
				}
			}
			if(notargets) {
				targetsList.remove("what would you like to use this on?");
				targetsList.add("there is nothing to use this on");
				return targetsList;
			}
			//do something to that item
			String targetName = Game.printPrompt(sc,targetsList);
			itemAction.add("you used the item on " + this.getCurrRoom().getItems().get(targetName).getName());
			return itemAction;
		}
		
	}
	
	public List<String> interact(Scanner sc){
		boolean noitems = true;
		List<String> possibleItems = new ArrayList<String>();
		for(Item item : this.getCurrRoom().getItems().values()) {
			if(item.getType() == 1) {
				noitems = false;
				possibleItems.add(item.getName());
			}
		}
		
		return null;
	}
	
	public List<String> move(Scanner sc){
		List<String> returnStringList = new ArrayList<>();
		List<String> moveOptions = new ArrayList<String>();
		moveOptions.add(Game.moveQuestion);
		if(this.getCurrRoom().getNorth() != null) {
			moveOptions.add("north");
		}
		if(this.getCurrRoom().getEast() != null) {
			moveOptions.add("east");
		}
		if(this.getCurrRoom().getSouth() != null) {
			moveOptions.add("south");
		}
		if(this.getCurrRoom().getWest() != null) {
			moveOptions.add("west");
		}
		String dir = Game.printPrompt(sc,moveOptions);
		//TODO: add hanndeling for moving through a locked door
		if(dir.equals("north")) {
			this.setCurrRoom(this.getCurrRoom().getNorth());
		}
		if(dir.equals("south")) {
			this.setCurrRoom(this.getCurrRoom().getSouth());
		}
		if(dir.equals("east")) {
			this.setCurrRoom(this.getCurrRoom().getEast());
		}
		if(dir.equals("west")) {
			this.setCurrRoom(this.getCurrRoom().getWest());
		}
		returnStringList.add("you entered " + this.getCurrRoom().getName());
		return returnStringList;
	}
	
	//end of class
}
