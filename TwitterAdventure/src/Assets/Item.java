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
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Item {
	//TODO: determine what values this class will use
	private String name;
	private String description;
	private String location;
	//flag check to interact with
	private String flag;
	//
	private String trigger;
	//type 0 is a regular item you can pick up
	//type 1 is a static item fixed to the room
	//type 2 is a dont know yet (not used yet)
	private int type;
	//chain is true if you can use this item on something
	private boolean chain;
	private String action;
	//Constructors-----------------------------------------------------------------------------------------------------
	Item(){
		this.name = "";
		this.description = "";
		this.location = "";
		this.trigger = "";
		this.flag = "";
		this.type = -1;
	}
	Item (String n){
		this.name = n;
		this.description = "";
		this.location = "";
		this.flag = "";
		this.trigger = "";
		this.type = -1;
		
	}
	Item (String n, String d){
		this.name = n;
		this.description = d;
		this.location = "";
		this.flag = "";
		this.trigger = "";
		this.type = -1;
	}
	//Getters-----------------------------------------------------------------------------------------------------------
	public String getDess() {
		return this.description;
	}
	public String getAction() {
		return this.action;
	}
	public String getName() {
		return this.name;
	}
	public int getType() {
		return type;
	}
	public String getLoc() {
		return this.location;
	}
	public String getFlag() {
		return this.flag;
	}
	public String getTrigger() {
		return this.trigger;
	}
	public boolean getChain() {
		return this.chain;
	}
	//Setters----------------------------------------------------------------------------------------------------------
	public void setDess(String d) {
		this.description = d;
	}
	public void setName(String n) {
		this.name = n;
	}
	public void setAction(String s) {
		this.action = s;
	}
	public void setTrigger(String t) {
		this.trigger = t;
	}
	//class object functions-------------------------------------------------------------------------------------------
	//TODO: implement other values and determine what values Item will have 
	/*
	 * parseItemJson
	 * fills the current Item object with the values gotten from the json filename
	 * filename: the json file to be read in
	 */
	public void parseItemJson(String filename) throws FileNotFoundException, IOException, ParseException{
		Object object;
		try {
			object =  new JSONParser().parse(new FileReader(filename));;
		}
		catch (FileNotFoundException e) {
			//the file is not in the folder
			System.err.println(filename + " item not read");
			return;
		}
		JSONObject json = (JSONObject) object;
		name = (String) json.get("name");
		description = (String) json.get("description");
		Long l  =  (Long) json.get("type");
		type = l.intValue();
		if(json.get("flag").equals("none")) {
			flag = "";
		}
		else {
			flag = (String) json.get("flag");
		}
		if(json.get("trigger").equals("none")) {
			trigger = "";
		}
		else {
			trigger = (String) json.get("trigger");
		}
		String l2 = (String) json.get("chain");
		int a = Integer.valueOf(l2);
		if(a == 0) {chain = false;}
		else {chain = true;}
		location = (String) json.get("location");
		action = (String) json.get("action");
	}
	//end of class
}
