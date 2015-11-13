//@author : Anshuman Suri - 2014021
//@author : Satyam Kumar - 2014096

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
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
		  writer.println("<!DOCTYPE html>");
		  writer.println("<html lang = 'en'>");
		  writer.println("<head>");
		  writer.println("<title>Like Predictor</title>");
		  writer.println("<meta charset = 'utf-8'>");
		  writer.println("<meta name = 'viewport' content = 'width = device-width, initial-scale = 1'>");
		  writer.println("<link rel = 'stylesheet' href = 'http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>");
		  writer.println("<script src = 'https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js'></script>");
		  writer.println("<script src = 'http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>");
		  writer.println("</head>");
		  writer.println("<body class='progress-bar progress-bar-striped active' id='progress-bar' role='progressbar'aria-valuenow='100' aria-valuemin='0' aria-valuemax='100'>");
		  writer.println("<div class='container text-center' style= 'vertical-align:middle;'>");
		  writer.println("<!-- <img src='https://media1.giphy.com/media/czyRCsoai6tZm/200.gif' class='img-circle'> -->");
		  writer.println("</div>");
		  writer.println("</body>");
		  writer.println("</html>");
		  writer.flush();
		  //writer.close();
		  RequestDispatcher ob = request.getRequestDispatcher("/ResultPage");
		  ob.include(request, response);
		  Result ret=LikePrediction.Runner(MY_ACCESS_TOKEN);
		  HttpSession session=request.getSession();
		  session.setAttribute("results", ret);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		response.sendRedirect("ResultPage");
	}

}
