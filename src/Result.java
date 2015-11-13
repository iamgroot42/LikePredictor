import java.util.ArrayList;
import java.util.HashMap;

public class Result {
	ArrayList<Long> predicted_likes; 
	ArrayList<Long> actual_likes; 
	HashMap<String,String> likers;
	double percentage_error;
	double absolute_error;
	public double getPercentage_error() {
		return percentage_error;
	}
	public void setPercentage_error(double percentage_error) {
		this.percentage_error = percentage_error;
	}
	public double getAbsolute_error() {
		return absolute_error;
	}
	public void setAbsolute_error(double absolute_error) {
		this.absolute_error = absolute_error;
	}
	public ArrayList<Long> getPredicted_likes() {
		return predicted_likes;
	}
	public void setPredicted_likes(ArrayList<Long> predicted_likes) {
		this.predicted_likes = predicted_likes;
	}
	public ArrayList<Long> getActual_likes() {
		return actual_likes;
	}
	public void setActual_likes(ArrayList<Long> actual_likes) {
		this.actual_likes = actual_likes;
	}
	public HashMap<String,String> getLikers() {
		return likers;
	}
	public void setLikers(HashMap<String,String> likers) {
		this.likers = likers;
	}
}
