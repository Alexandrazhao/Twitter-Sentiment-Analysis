
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

public class AnalyzeSentiment{

private int nums;
public static RedBlackBST<String,Double> sentiments;
private static List<Status> tw;
private static SearchTweets dd = new SearchTweets();
//private static QueryResult st;
private ArrayList<Double> avg = new ArrayList<Double>();
private static String[] words;


/*public static RedBlackBST loadSentiments(){
	sentiments = new RedBlackBST<String,Double>();
	//RedBlackBST<Value> value = new RedBlackBST<Value>();
	File infile = new File("sentiments.csv");
	Scanner scanner = null;
	try{
		scanner = new Scanner(infile);

	}catch (FileNotFoundException ex){
		System.err.println("Error: locations file not found.");
		
	}
while(scanner.hasNextLine()){
	String line = scanner.nextLine();
	StringTokenizer tokenizer = new StringTokenizer(line);
	String[] field = line.split(",");
	double score = Double.parseDouble(field[1]);

	Sentiments senti = new Sentiments(field[0],score);
	sentiments.put(senti.getSenti(), senti.getScore());
}
  scanner.close();
  return sentiments;
 
}*/

  public static RedBlackBST<String,Double> loadSentiments(String fname){
	sentiments = new RedBlackBST<String,Double>();
	//RedBlackBST<Value> value = new RedBlackBST<Value>();
	File infile = new File(fname);
	Scanner scanner = null;
	try{
		scanner = new Scanner(infile);

	}catch (FileNotFoundException ex){
		System.err.println("Error: locations file not found.");
		return null;
	}
while(scanner.hasNextLine()){
	String line = scanner.nextLine();
	StringTokenizer tokenizer = new StringTokenizer(line);
	String[] field = line.split(",");
	double score = Double.parseDouble(field[1]);

	Sentiments senti = new Sentiments(field[0],score);
	sentiments.put(senti.getSenti(), senti.getScore());
}
  scanner.close();
  return sentiments;
 
}

public AnalyzeSentiment(){

}

/*public static void collectTweets(){
GeoLocation gl = new GeoLocation(51.51316,-0.13118);
SearchTweets tweets= new SearchTweets(new Query("gay"),
					      50,gl,30,Query.ResultType.recent);
try{
	tweets.save("AllTweets.ser");
}catch(IOException ex){
	ex.printStackTrace();
}catch(ClassNotFoundException e){
        System.err.println("Error:Class not found");
       }

}*/

/*public void load(String filename) throws IOException{
	try{
		FileInputStream tem = new FileInputStream(filename);
	ObjectInputStream doi = new ObjectInputStream(tem);
	Object twp = doi.readObject();
	while(twp != null){
		Boolean added = false;
		for(Status t : tw){
			if(t.getText().equals(((Status)twp).getText()) && t.getUser().equals(((Status)twp).getUser()))
				added = true;
			if(added = false)
				tw.add((Status)twp);
    		twp = doi.readObject();
		}
	}
		doi.close();
		tem.close();
	}catch(IOException ex){
       	 System.err.println("Error: locations file not found.");
       }catch(ClassNotFoundException e){
        System.err.println("Error:Class not found");
       }

}*/

public static Double analyzeTweets(){
	//tweets = tweets.load("AllTweets.ser");
	
	try{
		tw = dd.load("AllTweets.ser");
}catch(IOException e){
	System.err.println("Error: locations file not found.");
}
    //tw = "I love dog and i have computer.";
System.out.println("The num of elements is:" + tw != null);
System.out.println("size of tw"+tw.size());
    double count = 0.0;
	double ave = 0.0;
    double getvalue = 0.0;

	//File infile = new File("sentiments.csv");
 //st.load("AllTweets.dat");
	//String[] lines = Files.readAllLines(new File("AllTweets.dat").toPath()).toArray(new String[0]);
 //tweets = st.load("AllTweets.dat").getTweets();

  for(int m = 0; m < tw.size(); m++){
  Status t = tw.get(m);
  String s = t.getText();
  System.out.println(s);
  words = s.toLowerCase().replaceAll("^\\w]","").split("\\s+");
  for(int i = 0; i<words.length; i++){
  	//getvalue = sentiments.get(words[i]);
  	//System.out.println(getvalue + " ");
  	if(sentiments.get(words[i]) != null){
  		count += sentiments.get(words[i]);
  		System.out.println(count+ " ");
  	}
  }
  ave = count/words.length;
  //avg.add(ave);

  //double k = sentiments.get()
  
  
  }
 //}

  
   return ave;
}

/*public static SearchTweets getRandomTweets(){
 GeoLocation gl = new GeoLocation(51.51316,-0.13118);
SearchTweets tweets= new SearchTweets(new Query(" "),
					      10000,gl,30,Query.ResultType.recent);
try{
	tweets.save("AllTweets.dat");
}catch(IOException ex){
	ex.printStackTrace();
}
return 
}*/

/*public static Integer getTargetTweets(){
	String words = "gay";
	int counter = 0;
 for(Status t : tw){
  String s = t.getText();
  if(s.toLowerCase().indexOf(words.toLowerCase()) != -1){
    counter++;
    System.out.println(counter + " ");
  }

 }
  return counter;
}*/
/*
public static Double analyzeTweetsfq(){
  double targetTweets = 0.0;
  double totalTweets = 0.0;
  double fq;
  targetTweets = getTargetTweets();
  totalTweets = 50;
  fq = targetTweets/totalTweets;
  System.out.println(fq + " ");
  return fq;
}*/






public static void main(String[] args){
	//GeoLocation gl = new GeoLocation(22.27832,114.17469); // London SoHo
	//dd.collectTweets(100,"gay",gl,20,Query.ResultType.recent);
	//tw = dd.load("AllTweets.ser");
   
	loadSentiments("sentiments.csv");
	analyzeTweets();
	//System.out.println(tw.size());
	//double k = 0;
  if(args.length != 0){
  	System.out.println("java SearchTweets");
  	System.exit(-1);
  }
//RedBlackBST<String,Double> sentiments = l
  
 /* AnalyzeSentiment target  = new AnalyzeSentiment();
  target.loadSentiments();
//target.getTargetTweets();
 try{
 	FileInputStream tempp = new FileInputStream("AllTweets.dat");
    	ObjectInputStream douu = new ObjectInputStream(tempp);
    	Object twp = douu.readObject();
    	while(twp != null){
    	Boolean added = false;
    	for(Status t : tweets){
    	   if(t.getText().equals(((Status)twp).getText()) && t.getUser().equals(((Status)twp).getUser()))   	  
    	   	k = target.analyzeTweets(t);
    	   System.out.println(k + " ");
    	}
    	}
    	douu.close();
    	tempp.close();
    }catch(IOException ex){
    	System.err.println("Error: locations file not found.");
    }catch(ClassNotFoundException e){
    	System.err.println("Error:Class not found");
    }
    
//target.analyzeTweetsfq();
/*for(int i = 0; i < sentiments.size(); i++){
	if(sentiments.get(i).getScore())
   }*/
  }
 }
