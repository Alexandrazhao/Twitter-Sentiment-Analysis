/* 
 * Represent data collected about tweeter.
 */
public class UserData {
	private String name;
	private int numtweets;
	
	public UserData(String thename, int n) {
		name = thename;
		numtweets = n;
	}
	
	public String getName() { return name;}
	public int numTweets() { return numtweets;}
	
	public String toString() {
		return numtweets + "   " + name; 
	}
}
