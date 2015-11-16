//@author : Anshuman Suri - 2014021
//@author : Satyam Kumar - 2014096

public class FVector {
	private long offset;
	private long attachments;
	private long attachmentsSQ;
	private long comments;
	private long commentsSQ;
	private long place;
	private long time_of_day;
	private long time_of_daySQ;
	private long post_length;
	private long post_lengthSQ;
	private long tags;
	private long tagsSQ;
	private long picture;
	private long shares;
	private long sharesSQ;
	private long with_tags;
	private long with_tagsSQ;
	private long number_of_friends;
	private long number_of_friendsSQ;
	private String link;
	public long getTime_of_daySQ() {
		return time_of_daySQ;
	}
	public void setTime_of_daySQ(long time_of_daySQ) {
		this.time_of_daySQ = time_of_daySQ;
	}
	public long getWith_tagsSQ() {
		return with_tagsSQ;
	}
	public void setWith_tagsSQ(long with_tagsSQ) {
		this.with_tagsSQ = with_tagsSQ;
	}
	public long getAttachmentsSQ() {
		return attachmentsSQ;
	}
	public void setAttachmentsSQ(long attachmentsSQ) {
		this.attachmentsSQ = attachmentsSQ;
	}
	public long getCommentsSQ() {
		return commentsSQ;
	}
	public void setCommentsSQ(long commentsSQ) {
		this.commentsSQ = commentsSQ;
	}
	public long getPost_lengthSQ() {
		return post_lengthSQ;
	}
	public void setPost_lengthSQ(long post_lengthSQ) {
		this.post_lengthSQ = post_lengthSQ;
	}
	public long getTagsSQ() {
		return tagsSQ;
	}
	public void setTagsSQ(long tagsSQ) {
		this.tagsSQ = tagsSQ;
	}
	public long getSharesSQ() {
		return sharesSQ;
	}
	public void setSharesSQ(long sharesSQ) {
		this.sharesSQ = sharesSQ;
	}
	public long getNumber_of_friendsSQ() {
		return number_of_friendsSQ;
	}
	public void setNumber_of_friendsSQ(long number_of_friendsSQ) {
		this.number_of_friendsSQ = number_of_friendsSQ;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
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
