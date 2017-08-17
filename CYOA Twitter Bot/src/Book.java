package us.jamestalks.learning;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.List;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Book {
	HashMap<Integer,Page> book;
	private static final String FILENAME = "C:\\Users\\james\\Desktop\\input.txt";
	
	//Constructor for Book
	public Book(){
		book = new HashMap<Integer,Page>();
	}
	
	public void constructMap(){
		int i = 0;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) {
	//my code starts here --------------------------------------------------------------------------------
				
				Page tempPage = createPage(sCurrentLine);
				i = tempPage.Pid;
				book.put(i, tempPage);
				//System.out.println(sCurrentLine);
	// end ends here ------------------------------------------------------------------------------------
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

				}
			}
	}
	
	public Page createPage(String line){
		Page newpage = new Page();
		String[] parts = line.split("-");
		newpage.prompt = parts[0];
		//System.out.println(parts[1]);
		newpage.Pid = Integer.parseInt(parts[1]);
		
		if(!parts[2].equals("*")){
		newpage.Q1 =  parts[2];
		newpage.id1 = Integer.parseInt(parts[3]);
		}
		if(!parts[4].equals("*")){
		newpage.Q2 =  parts[4];
		newpage.id2 = Integer.parseInt(parts[5]);
		}
		if(!parts[6].equals("*")){
		newpage.Q3 =  parts[6];
		newpage.id3 = Integer.parseInt(parts[7]);
		}
		if(!parts[8].equals("*")){
		newpage.Q4 =  parts[8];
		newpage.id4 = Integer.parseInt(parts[9]);
		}
		System.out.println(newpage.Pid);
		return newpage;
	}
	
}
