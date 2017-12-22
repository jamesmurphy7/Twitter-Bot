package Assets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

public class Map {
	Room[][] map;
	int ysize;
	int xsize;
	//Constructors-----------------------------------------------------------------------------------------------------
	public Map(){
		this.map = new Room[100][100];
		ysize = 0;
	}
	public Map(int y, int x){
		this.map = new Room[y][x];
		ysize = y;
		xsize = x;
	}
	//Getters-----------------------------------------------------------------------------------------------------------
	public Room getRoomAt(int y, int x) {
		return map[y][x];
	}
	//Setters----------------------------------------------------------------------------------------------------------
	public void setRoomAt(int y, int x, Room r) {
		this.map[y][x] = r;
	}
	//class object functions-------------------------------------------------------------------------------------------
	/*
	 * readmap
	 * creates and fills the matrix of rooms of the map class
	 * Parameter: the filename of the map (currently gamefiles/map.txt)
	 */
	//TODO: handle constructing room by reading JSON files
	public void readmap(String filename) {
		File inputfile = new File(filename);
		Scanner sc = null;
		try {
			sc = new Scanner(inputfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int y = 0;
		while(sc.hasNextLine()) {
			int x = 0;
			String line = sc.nextLine();
			for(int i = 0;i<line.length();i++) {
				Room tempRoom;
				if(line.substring(i,i+2).equals("  ")) {
					tempRoom = null;
				}
				else {
					//TODO: change this to the constructor of a filename (append.json to substring)
					tempRoom = new Room(line.substring(i,i+2));
				}
				setRoomAt(y,x,tempRoom);
				i += 2;
				x++;
			}
			y++;
		}
	}
	/*
	 * linkRooms
	 * adds the link rooms to each of the n,s,e,w room members
	 */
	public void linkRooms() {
		for(int i = 0;i<ysize-1;i++) {
			for(int j = 0; j<xsize-1; j++) {
				if(map[i][j] != null && map[i][j+1] != null) {
					//then link those two rooms
					map[i][j].setEast(map[i][j+1]);
					map[i][j+1].setWest(map[i][j]);
				}
				if(map[i][j] != null && map[i+1][j] != null) {
					map[i][j].setSouth(map[i+1][j]);
					map[i+1][j].setNorth(map[i][j]);
				}
			}
		}
		//link far right collom
		for(int i = 0;i<ysize-1;i++) {
			int j = xsize-1;
			if(map[i][j] != null && map[i+1][j] != null) {
				map[i][j].setSouth(map[i+1][j]);
				map[i+1][j].setNorth(map[i][j]);
			}
		}
		//link far bottom collom
		for(int j = 0;j<xsize-1;j++) {
			int i = ysize -1;
			if(map[i][j] != null && map[i][j+1] != null) {
				map[i][j].setEast(map[i][j+1]);
				map[i][j+1].setWest(map[i][j]);
			}
		}
	}
	
//end of class
}
