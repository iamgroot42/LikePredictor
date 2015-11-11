//@author : Anshuman Suri - 2014021
//@author : Satyam Kumar - 2014096

public class FVector {
	private long offset;
	private long attachments;
	private long comments;
	private long place;
	private long time_of_day;
	private long post_length;
	private long tags;
	private long picture;
	private long shares;
	private long with_tags;
	private long number_of_friends;
	public FVector()
	{
		offset=1;
		attachments=comments=place=time_of_day=post_length=tags=0;
		picture=shares=with_tags=0;
		number_of_friends=0;
	}
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	public long getAttachments() {
		return attachments;
	}
	public void setAttachments(long attachments) {
		this.attachments = attachments;
	}
	public long getComments() {
		return comments;
	}
	public void setComments(long comments) {
		this.comments = comments;
	}
	public long getPlace() {
		return place;
	}
	public void setPlace(long place) {
		this.place = place;
	}
	public long getTime_of_day() {
		return time_of_day;
	}
	public void setTime_of_day(long time_of_day) {
		this.time_of_day = time_of_day;
	}
	public long getPost_length() {
		return post_length;
	}
	public void setPost_length(long post_length) {
		this.post_length = post_length;
	}
	public long getTags() {
		return tags;
	}
	public void setTags(long tags) {
		this.tags = tags;
	}
	public long getPicture() {
		return picture;
	}
	public void setPicture(long picture) {
		this.picture = picture;
	}
	public long getShares() {
		return shares;
	}
	public void setShares(long shares) {
		this.shares = shares;
	}
	public long getWith_tags() {
		return with_tags;
	}
	public void setWith_tags(long with_tags) {
		this.with_tags = with_tags;
	}
	public long getNumber_of_friends() {
		return number_of_friends;
	}
	public void setNumber_of_friends(long number_of_friends) {
		this.number_of_friends = number_of_friends;
	}
}
