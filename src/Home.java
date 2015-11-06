import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.types.Post;
import com.restfb.types.User;

public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");

		String MY_ACCESS_TOKEN = "";
		String secret="87ea15d7eef3d69efb996f7e29f4f151";
		String id="412912138911681";
		String authURL = "https://graph.facebook.com/oauth/access_token?client_id="+id+"&redirect_uri=http://localhost:8080/Facebook/Home&client_secret="+secret+"&code="
				+ code;
		URL url = new URL(authURL);
		String result = readURL(url);
		String[] pairs = result.split("&");

		for (String pair : pairs) {
			String[] kv = pair.split("=");
			if (kv[0].equals("access_token")) {
				MY_ACCESS_TOKEN = kv[1];
			}
		} // end of for loop

		FacebookClient facebookClient = new DefaultFacebookClient(
				MY_ACCESS_TOKEN);
		Connection<Post> updates = null;
		
		try {
			updates = facebookClient.fetchConnection("me/statuses", Post.class);
		} catch (FacebookException e) {			
			e.printStackTrace();
		}
		
		System.out.println(MY_ACCESS_TOKEN);
		
		List<Post> friendsList = updates.getData();
		request.setAttribute("friendsList",friendsList);
		
		for (Post user : friendsList) {
			System.out.println(user.getId() + " : " + user.getMessage());
		}
		
//		getServletConfig().getServletContext().getRequestDispatcher("/FriendsList.jsp").forward(request, response);
	}
		
	private String readURL(URL url) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = url.openStream();
		int r;
		while ((r = is.read()) != -1) {
			baos.write(r);
		}
		return new String(baos.toByteArray());
	}
}