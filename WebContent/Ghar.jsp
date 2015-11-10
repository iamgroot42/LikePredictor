<%@ page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<html>
<head>
<title>Facebook Like Predictor</title>
</head>
<body>
	<h2>
		<a
			href="https://graph.facebook.com/oauth/authorize?client_id=412912138911681&scope=email,user_friends,user_posts&redirect_uri=<%=URLEncoder.encode("http://localhost:8080/Facebook/Home")%>">Click
			Here</a>
	</h2>
</body>
</html>