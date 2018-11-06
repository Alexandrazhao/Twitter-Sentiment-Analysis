/*
 * Class to support search of Twitter's tweets using twitter4j API.
 * Concatenates tweets to a file.
 */

import twitter4j.*;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Constructing a SearchTweets object will perform a single search
 * and store them in a List.
 */
public class SearchTweets {
    private static final int MAXTWEETS = 100; // max tweets at once
    private List<Status> tweets; // tweets from query
    private Query query; // query that generated the tweets
	private static RedBlackBST<String,Double> sentiments;
    /**
     * Generate tweets based on typical Twitter search string.
     * @param querystring (See https://dev.twitter.com/docs/using-search )
     */
    public SearchTweets(String querystring) {
	this(new Query(querystring),20,null,0.0,Query.ResultType.recent);
    }

    public SearchTweets(){

    }

    /**
     * Retrieve tweets based on typical Twitter search string.
     * @param query valid Query object
     * @param count number of tweets to get (max 100)
     * @param loc  Central point of tweets (null to get all tweets)
     * @param radius Radius (in km) of locations searched
     * @param type type of tweets to get
     */
    public SearchTweets(Query query,int count, GeoLocation loc,
			double radius, Query.ResultType type) {
	this.query = query;
	if (count > 0 && count < MAXTWEETS) // limit tweets we get
	    query.setCount(count);
	query.setResultType(type);
	if (loc != null) query.setGeoCode(loc,radius,Query.KILOMETERS);
	Twitter twitter = new TwitterFactory().getInstance();
	QueryResult result = null;
	try {
	    result = twitter.search(query);
	    tweets = result.getTweets(); // store retrieved tweets
	}
	catch (TwitterException te) {
	    tweets = null;
	    te.printStackTrace();
	    System.out.println("Failed to search tweets: " + te.getMessage());
	}
    }

  
   
  

/*public Double analyzeTweets(){
	//tweets = tweets.load("AllTweets.ser");
	
/*	try{
		tweets = load("AllTweets.ser");

}catch(IOException e){
	System.err.println("Error: locations file not found.");
}*/

/*    double count = 0.0;
	double ave = 0.0;
    double value = 0.0;
	//File infile = new File("sentiments.csv");
 //st.load("AllTweets.dat");
	//String[] lines = Files.readAllLines(new File("AllTweets.dat").toPath()).toArray(new String[0]);
 //tweets = st.load("AllTweets.dat").getTweets();
 for(Status t : tweets){
 	System.out.println("load");
  String s = t.getText();
  System.out.println(s);
  String[] words = s.toLowerCase().replaceAll("^\\w]","").split("\\s+");
  for(int i = 0; i<words.length; i++){
  	if(sentiments.get(words[i]) != null){
  		value += sentiments.get(words[i]);
  		//count += sentiments.get(words[i]);
  		
  	}
  }
  ave = value/words.length;
  System.out.println(ave+ " ");
  //avg.add(ave);

  //double k = sentiments.get()
  
  
  
 //}

  }
   return ave;
}*/

//return the value associated with the given key;



 /*  public Integer getTargetTweets(){
	String words = "you";
	int counter = 0;
 for(Status t : tweets){
  String s = t.getText();
  if(s.toLowerCase().indexOf(words.toLowerCase()) != -1){
    counter++;
    System.out.println(counter + " ");
  }

 }
  return counter;
}*/

/*public Double analyzeTweetsfq(){
  double targetTweets = 0.0;
  double totalTweets = 0.0;
  double fq;
  targetTweets = getTargetTweets();
  totalTweets = tweets.size();
  fq = targetTweets/totalTweets;
  System.out.println(fq + " ");
  return fq;
}*/
    /**
     * @return number of tweets in this object
     */
    public int numTweets() {
	if (tweets == null) return 0;
	return tweets.size();
    }

    /**
     * Add a method to return an arraylist consisting of every userid and the
     * number of tweets by that user.
     */
    public ArrayList<UserData> getCounts() {
	if (tweets == null) return null;
	ArrayList<String> results = new ArrayList<String>();
	// get info about all tweets
	for (Status tweet : tweets) {
	    String name = tweet.getUser().getScreenName();
	    results.add(name);
	}
	Collections.sort(results);
	ArrayList<UserData> userdata = new ArrayList<UserData>();

	// Count tweets by username, assume userdata is sorted by name.
	for (int i = 0; i < results.size(); i++) {
	    String name = results.get(i);
	    int count = 0;
	    // loop over all of same name, update count
	    while (i < results.size() && name.equals(results.get(i))) {
		i++;
		count++;
	    }
	    userdata.add(new UserData(name,count));
	    i--;
	}
	return userdata;
    }

    /**
     * Print all tweets to standard output.
     */
    public void print() {
	if (tweets == null) return;
	// Print out info about all tweets
	for (Status tweet : tweets) {
	    System.out.println("\n\n@" + tweet.getUser().getScreenName() +
			       " - " + tweet.getText());
	    System.out.println("date: " + tweet.getCreatedAt());
	    //System.out.println("User: " + tweet.getUser());
	    System.out.println("Reply to: " + tweet.getInReplyToScreenName());
	}
    }

    /**
     * Save all tweets to a file.  Saves all Status data
     * as serialized objects.
     * @param filename Name of output file.  Will append
     * all unique tweets to any file of same name.
     */
    public void save(String filename) {
	// TODO
    	
    	try {
    		//Twitter tw = new Twitter();
	    FileOutputStream temp = new FileOutputStream(filename);
	    ObjectOutputStream dou = new ObjectOutputStream(temp);
	    for(Status t : tweets){
	    	dou.writeObject(t);
	    }
	    
	    temp.close();
	    dou.close();
	}catch(EOFException ef){
		System.err.println("Error: locations file not found.");
	}
	 catch (IOException ex) {
	    System.err.println("Error: locations file not found.");
	   
	}
	
    	
    }

    /**
     * Load tweets from a saved file of Status objects.
     * Appends to current this.tweets all tweets not already stored. 
     * @param filename Name of input file.
     */
    public List<Status> load(String filename) throws IOException {
    	ObjectInputStream objectinputstream = null;
    	try{
    	FileInputStream tempp = new FileInputStream(filename);
    	objectinputstream = new ObjectInputStream(tempp);
    	List<Status> read = (List<Status>) objectinputstream.readObject();
    	//Object twp = douu.readObject();
    	//Status twq = (Status)twp;
    	//while(twp != null){
    	//Boolean added = false;
    	/*for(Status t : read){
    	   if(!tweets.contains(t)){
    	   	tweets.add(t);
    	   	System.out.println("added text");
    	   } 
    	   } */ 
    	   return read;	  
    	   
       }catch(IOException e){
       	e.printStackTrace();
       }catch(Exception e){

       }

       return null;
	// TODO
    }
   /* public void load(String filename) throws IOException {
    	try{
    	FileInputStream tempp = new FileInputStream(filename);
    	ObjectInputStream douu = new ObjectInputStream(tempp);
    	Object twp = douu.readObject();
    	while(twp != null){
    	Boolean added = false;
    	for(Status t : tweets){
    	   if(t.getText().equals(((Status)twp).getText()) && t.getUser().equals(((Status)twp).getUser()))   	  
    	   	added = true;
    	}
    	if(added == false)
    		tweets.add((Status)twp);
    		twp = douu.readObject();
    	
    	
    	//twp = douu.readObject();
    	
        }
        douu.close();
    	tempp.close();
       } catch(IOException ex){
       	 System.err.println("Error: locations file not found.");
       }catch(ClassNotFoundException e){
        System.err.println("Error:Class not found");
       }

	// TODO
    }*/
    /**
     * Load location data from a file.
     * @param fname File from which to load data
     * @return Arraylist of all cities in the file.
     */
    public static ArrayList<Location> loadLocations(String fname) {
	ArrayList<Location> locations = new ArrayList<Location>();
	File infile = new File(fname);
	Scanner scanner = null;
	try {
	    scanner = new Scanner(infile);
	} catch (FileNotFoundException ex) {
	    System.err.println("Error: locations file not found.");
	    return null;
	}
	// Loop over lines and split into fields we keep.
	while (scanner.hasNextLine()) {
	    String line = scanner.nextLine();
	    StringTokenizer tokenizer = new StringTokenizer(line);
	    String[] fields = line.split(",");
	    long pop = Long.parseLong(fields[4]);
	    if (pop > 100000) { // Ignore small towns.
				// See Location.java to figure out these fields.
		Location loc = new Location(fields[0],fields[1],
					    Double.parseDouble(fields[2]),Double.parseDouble(fields[3]),pop);
		locations.add(loc);
		//System.out.println("Added " + loc.toString());
	    }
			
	}
	scanner.close();
	return locations;
    }

    /*public List<Status> getTwee(){
      

    }*/
	
	
    /**
     * Collect tweets and add all resulting tweets to a file.
     * @param numtweets Max number of tweets to gather
     * @param searchwords String of all words that will form twitter searches
     * @param loc Location to search for tweets
     * @param radius Radius (kilometers) about loc to search
     * @param type  Type of results to find
     */
    public static void collectTweets(int numtweets, String searchwords, GeoLocation loc,
				     double radius, Query.ResultType type, String outfile) {

	StringTokenizer searchtok = new StringTokenizer(searchwords);
	while (searchtok.hasMoreTokens()) {
	    String word = searchtok.nextToken();
	    SearchTweets tweets= new SearchTweets(new Query(word),numtweets,loc,radius, type);
	    System.out.println("Num Tweets: " + tweets.numTweets());
	    tweets.print();
	    tweets.save(outfile);				
	}
    }

    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args
     */
    public static void main(String[] args) {
	if (args.length != 0) {
	    System.out.println("java SearchTweets");
	    System.exit(-1);
	}

	 /*
	 * TODO: Right now this just searches for these words in big cities.
	 */
	String searchWords = "#Bard #Food2Families";
	int maxnum = 10; // max num tweets to get at one time
	final double radius = 10; // radius around location to search

	GeoLocation gl = new GeoLocation(22.27832,114.17469); // London SoHo
	// 10 tweets within 20 Kilometers
	SearchTweets tweets= new SearchTweets(new Query("gay"),
					      50,gl,20,Query.ResultType.recent);
	System.out.println("Num Tweets: " + tweets.numTweets());
	
	//try{
		tweets.save("AllTweets.ser");
		//System.out.println(tweets.size() + "-----");
	    //tweets.load("AllTweets.ser");
  //}
  //catch(IOException ex){
  //ex.printStackTrace();
  //}
   
	//tweets.analyzeTweets();
	tweets.print();
	//System.out.println("Frequency of tweets about words:" + tweets.analyzeTweetsfq());
    //RedBlackBST<String,Double> sentiments = loadSentiments("sentiments.csv");

    //this.analyzeTweets();
        //RedBlackBST<String,Double> senti = sentiments.get(i).getScore();
		}
    }
 /*  // OMIT FOR NOW

 // Get citites data.
 ArrayList<Location> locations = loadLocations("us-cities.txt");
 long minpop = 6000000L; // min city size to consider

 // WARNING: If you get too much data, you will be     //
 // locked out for 15 minutes!                         //
 for (int i = 0; i < locations.size(); i++ ) {
     if (locations.get(i).getPopulation() > minpop) {
  System.out.println(locations.get(i) + "===================");
  GeoLocation loc = locations.get(i).getLocation();
  collectTweets(maxnum,searchWords,loc,radius,
         Query.ResultType.recent,"alltweets.ser");
     }
 }*/
 



	

   



