

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
		session.invalidate();
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
		writer.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>");
		writer.println("<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>");
		writer.println("<body>");
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
				writer.println("<div class=\"progress\">");
				double yoda=(((double)mapping.get(iterator))/(double)tot)*(100.0);
				writer.println("<div class=\"progress-bar progress-bar-striped progress-bar-"+type+"\" role=\"progressbar\" aria-valuenow=\""+yoda+"\"aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"+yoda+"%\">");
				writer.println("<span style='color:black; position:absolute; display:block; width: 100%;'>"); 
				writer.println(iterate.get(iterator));
				writer.println("</span>");
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
//			writer.println("Actual"+"          "+"Predicted");
//			for(int i=0;i<actual.size();++i)
//			{
//				writer.println(actual.get(i)+"          "+predicted.get(i));
//			}
		}
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
