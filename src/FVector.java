
public class FVector {
	int offset;
	int time_of_day;
	long number_of_friends;
	int post_length;
	int emoticons;
	int hashtags;
	public FVector()
	{
		offset=1;
		emoticons=0;
		hashtags=0;
	}
	public int getOffset() {
		return offset;
	}
	public int getTime_of_day() {
		return time_of_day;
	}
	public void setTime_of_day(int time_of_day) {
		this.time_of_day = time_of_day;
	}
	public long getNumber_of_friends() {
		return number_of_friends;
	}
	public void setNumber_of_friends(long number_of_friends) {
		this.number_of_friends = number_of_friends;
	}
	public int getPost_length() {
		return post_length;
	}
	public void setPost_length(int post_length) {
		this.post_length = post_length;
	}
	public int getEmoticons() {
		return emoticons;
	}
	public void setEmoticons(int emoticons) {
		this.emoticons = emoticons;
	}
	public int getHashtags() {
		return hashtags;
	}
	public void setHashtags(int hashtags) {
		this.hashtags = hashtags;
	}
}
