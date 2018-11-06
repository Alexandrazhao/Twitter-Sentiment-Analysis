public class Sentiments{
	
	private String sentiments;
	private Double score;

	
	public Sentiments(String sentiments, Double score){
      
      this.sentiments = sentiments;
      this.score = score;

	}

    public String getSenti(){
    	return sentiments;
    }
    
    public void setSenti(){
    	this.sentiments = sentiments;
    }

    public double getScore(){
      return score;

    }
    public void setScore(double score){
    	this.score = score;
    }

   /* pubic String toString(){

    	return sentiments + ", " + score;
    }*/
}