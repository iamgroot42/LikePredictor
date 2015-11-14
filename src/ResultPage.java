

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ResultPage
 */
@WebServlet("/ResultPage")
public class ResultPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResultPage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		HttpSession session=request.getSession();
		Result ret=(Result)session.getAttribute("results");
		//		session.invalidate();
		writer.println("<html lang='en'>");
		writer.println("<head>");
		writer.println("<meta http-equiv='content-type' content='text/html; charset=UTF-8'>");
		writer.println("<meta charset='utf-8'>");
		writer.println("<title>Like Predictor : Results</title>");
		writer.println("<meta name='generator' content='Bootply' />");
		writer.println("<meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>");
		writer.println("<link href='css/bootstrap.min.css' rel='stylesheet'>");
		writer.println("<link href='css/styles.css' rel='stylesheet'>");
		writer.println("</head>");
		
		
		writer.println("<body>");
		
		writer.println("<div class='padding'>");
			writer.println("<div class='row well'>");
				writer.println("<div class='col-sm-12'>");
					writer.println("<div class='panel panel-default'>");
						writer.println("<div class='panel-thumbnail'></div>");
							writer.println("<div class='panel-body'>");
								writer.println("<h class='lead'>");
									writer.println("<center>");
										writer.println(" Results!");
									writer.println(" </center>");
								writer.println("</h>");
								writer.println("<p><center>");
									writer.println("<img src='https://lh3.googleusercontent.com/uFp_tsTJboUY7kue5XAsGA=s28' width='28px' height='28px'>");
								writer.println("</center></p>");
							writer.println("</div>");
					writer.println("</div>");
				writer.println("</div>");
			writer.println("</div>");
		writer.println("</div>");
		if(ret!=null)
		{
			//			writer.println("Training error : "+ret.getPercentage_error()+"%");
			HashMap<String,Long> mapping=WhoWillLike.getMapping();
			HashMap<String,String> iterate=ret.getLikers();
			long tot=WhoWillLike.getTotal();
			int i=0;
			String type;
			for(String iterator : iterate.keySet())
			{
				if(i%4==0)
				{
					type="success";
				}
				else if(i%4==1)
				{
					type="info";
				}
				else if(i%4==2)
				{
					type="warning";
				}
				else
				{
					type="danger";
				}
				writer.println("<div class='container' style='margin-left:0%'>");
				writer.println("<div class=\"progress\" style='margin-bottom:0.2%'>");
				double yoda=(((double)mapping.get(iterator))/(double)tot)*(100.0);
				writer.println("<div class=\"progress-bar progress-bar-striped progress-bar-"+type+"\" role=\"progressbar\" aria-valuenow=\""+yoda+"\"aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"+yoda+"%\">");
				writer.println("<span style='color:black; position:absolute; display:block; width: 100%;'>"); 
				writer.println(iterate.get(iterator));
				writer.println("</span>");
				writer.println("</div>");
				writer.println("</div>");
				writer.println("</div>");
				i++;
			}
			writer.println("Absolute training error : "+ret.getAbsolute_error());
			writer.println("<br>");
			writer.println("Percentage training error : "+ret.getPercentage_error());
			writer.println("<br>");
			ArrayList<Long> actual=ret.getActual_likes();
			ArrayList<Long> predicted=ret.getPredicted_likes();
			writer.println("<div class='column col-sm-12 col-xs-11' id='main'>");
			writer.println("<div class='padding'>");
			writer.println("<div class='row well'>");
			int j = 0, k;
			long end;
			for(String x:ret.getPost_links())
			{
				writer.println("<div class='col-sm-6'>");
					writer.println("<div class='panel panel-default'>");
						writer.println("<div class='panel-heading'>");
							writer.println("<h4>");
								writer.println("<a href='" + x + "'>Link to Post</a>");
							writer.println("</h4>");
						writer.println("</div>");
						writer.println("<div class='panel-body'>");
							writer.println("<div class='list-group'>");
								writer.println("<p class='list-group-item'>Number of Predicted Likes :</p>");
								writer.println("<p class='list-group-item'>Number of Actual Likes :</p>");
							writer.println("</div>");
						writer.println("</div>");
					writer.println("</div>");
				writer.println("</div>");
				//writer.println("<a href=\""+x+"\">Click here to view post</a>");
//				writer.println("Actual Likes : " + actual.get(j) /*+ "; Predicted Likes : " + predicted.get(j)*/);
//				writer.print("Likers : ");
//				//end = predicted.get(j); k = 0;
//				end = actual.get(j); k = 0;
//				for(String str : mapping.keySet()) {
//					if(k++ == actual.get(j)) break;
//					writer.print(iterate.get(str));
//					if(k != end) writer.print(", ");
//				}
//				writer.println("<br>");
//				writer.println("<br>");
//				j++;
			}
			//			writer.println("Actual"+"          "+"Predicted");
			//			for(int i=0;i<actual.size();++i)
			//			{
			//				writer.println(actual.get(i)+"          "+predicted.get(i));
			//			}
		}
		writer.println("</div>");
		writer.println("</div>");
		writer.println("</div>");
		writer.println("<script src='//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js'></script>");
		writer.println("<script src='js/bootstrap.min.js'></script>");
		writer.println("<script src='js/scripts.js'></script>");
		writer.println("</body>");
		writer.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
