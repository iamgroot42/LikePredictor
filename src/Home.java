//@author : Anshuman Suri - 2014021
//@author : Satyam Kumar - 2014096

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String APP_ID = "412912138911681";
    public static String APP_SECRET = "87ea15d7eef3d69efb996f7e29f4f151";

    /**
     * Default constructor. 
     */
    public Home() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    private String readURL(URL url) throws IOException {
    	 
    	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	  InputStream is = url.openStream();
    	 
    	  int r;
    	 
    	  while ((r = is.read()) != -1) {
    	   baos.write(r);
    	  }
    	 
    	  return new String(baos.toByteArray());
    	 
    	 }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String code = request.getParameter("code");
		  String URLEncodedRedirectURI = URLEncoder.encode("http://localhost:8080/Facebook/Home");
		  String MY_ACCESS_TOKEN = "";
		 
		  String authURL = "https://graph.facebook.com/oauth/access_token?" +
		                "client_id=" + Home.APP_ID + "&" +
		                "redirect_uri=" + URLEncodedRedirectURI + "&" +
		                "client_secret=" + Home.APP_SECRET + "&" +
		                "code=" + code;
//		  response.getWriter().print("URL : "+authURL);
		  URL url = new URL(authURL);
		 
		  String result = readURL(url);
		  String[] pairs = result.split("&");
		 
		  for (String pair : pairs) {
		 
		   String[] kv = pair.split("=");
		   if (kv[0].equals("access_token")) {
		    MY_ACCESS_TOKEN = kv[1];
		   }
		  }
		  response.getWriter().print(MY_ACCESS_TOKEN);
		  double wtaf=LikePrediction.Runner(MY_ACCESS_TOKEN);
		  response.getWriter().print(wtaf);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
