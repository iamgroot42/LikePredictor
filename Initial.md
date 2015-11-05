We plan on using machine learning along with some tag-classification (tentative) for posts for predicting the number of likes a post is likely to receive.A linear-regression model may be trained and used for predicting the number of likes for a user's posts, while a logistic regression model (many-vs-all) may be used for predicting the chances of a friend liking their status (we may use simple histograms,based on performance).

Proposed features for like prediction :
- Age
- Gender
- Time of post
- Size of post (expected to play a small role)
- Size of friend list
- Similarity with any trending topics (would tend to fetch higher likes)
- Tag analysis (most probably a no-no : overkill)
- Extended friend list (if post included other people ; yet again overkill)


Proposed features for pinpointing friend who would like a post :
- Regularity
- Relationship status
- Number of mutual friends
- Any tagged photos/etc

(Or we could go with the plain probability model for each user..much faster and might turn to be more accurate).

Code : 

- .getLikes() returns a NamedFacebookType when called on a StatusMessage.
- .getMessage() returns the content of the post. 
- .fetchObjects() returns a list of objects when called on FacebookClient.


