
public class Word {
	String word;
	int count;
	
	//default constructor for word
	public Word() {
		word = "";
		count = 0;
	}
	//constructor for word
	public Word(String w, int c) {
		word = w;
		count = c;
	}
	public void increment() {
		count++;
	}
	
	public int get_count() {
		return count;
	}
}
