

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ResultPage")
public class ResultPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		HttpSession session=request.getSession();
		Result ret=(Result)session.getAttribute("results");
		try
		{
			writer.println("<html lang='en'>");
			writer.println("<head>");
			writer.println("<meta http-equiv='content-type' content='text/html; charset=UTF-8'>");
			writer.println("<meta charset='utf-8'>");
			writer.println("<title>Like Predictor : Results</title>");
			
			//First pie chart :
			writer.print("<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
			writer.print("<script type=\"text/javascript\">");
			writer.print("google.load('visualization', '1.0', {'packages':['corechart']});");
			writer.print("google.setOnLoadCallback(drawChart);");
			writer.print("function drawChart() {");
			writer.print("var data = new google.visualization.DataTable();");
			writer.print("data.addColumn('string', 'Name');");
			writer.print("data.addColumn('number', 'Likes');");
			writer.print("data.addRows([");
			HashMap<String,Long> mapping=WhoWillLike.getMapping();
			HashMap<String,String> iterate=ret.getLikers();
			ArrayList<Long> actual=ret.getActual_likes();
			ArrayList<Long> predicted=ret.getPredicted_likes();
			long tot=WhoWillLike.getTotal();
			int i=0;
			for(String iterator : iterate.keySet())
			{
				if(i>=10) break; //Top 10 only
				writer.print("['");
				writer.print(iterate.get(iterator));
				writer.print("',");
				writer.print(mapping.get(iterator));
				writer.print("],");
				i++;
				tot-=mapping.get(iterator);
			}
			if(tot>0)
			{
				writer.print("['Others',"+tot+"],");
			}
			writer.print("]);");
			writer.print("var options = {'title':'Distrubution of likes'};");
			writer.print("var chart = new google.visualization.PieChart(document.getElementById('chart_div'));");
			writer.print("chart.draw(data, options);");
			writer.print("} </script>");
			writer.println("<meta name='generator' content='Bootply' />");
			writer.println("<meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>");
			writer.println("<link href='css/bootstrap.min.css' rel='stylesheet'>");
			writer.println("<link href='css/styles.css' rel='stylesheet'>");
			writer.println("<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\">");
			writer.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>");
			writer.println("<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>");
			
			//Second line chart :
			writer.print(" <script type=\"text/javascript\"src=\"https://www.google.com/jsapi?autoload={ 'modules':[{ 'name':'visualization','version':'1','packages':['corechart']}]}\"></script>");
			writer.print("<script type=\"text/javascript\">");
			writer.print("google.setOnLoadCallback(drawChart);");
			writer.print("function drawChart() {");
			writer.print("var data = google.visualization.arrayToDataTable([");
			writer.print(" ['Number', 'Predicted', 'Actual'],");
			int nono,hehe;
			nono=predicted.size();
			for(hehe=0;hehe<nono;hehe++)
			{
				writer.print("['"+(hehe+1)+"',");
				writer.print(predicted.get(hehe)+",");
				writer.print(actual.get(hehe)+"],");
			}
			writer.print("]);");
			writer.print("var options = {");
			writer.print("hAxis: { textPosition: 'none' },");
			writer.print("title: 'Actual v/s Prediction',");
			writer.print("curveType: 'function',");
			writer.print("legend: { position: 'bottom' }");
			writer.print("};");
			writer.print("var chart = new google.visualization.LineChart(document.getElementById('potato'));");
			writer.print(" chart.draw(data, options); }");
			writer.print(" </script>");
			writer.println("</head>");
			writer.println("<body>");
			
			//Render chart :
			writer.println("<div class=\"row\">");
			writer.println("<div style=\"width:50%; height:50%;\" class=\"col-sm-6\" id=\"chart_div\"></div>");
			writer.println("<div style=\"width:50%; height:50%;\" class=\"col-sm-6\" id=\"potato\"></div>");
			writer.println("</div>");			
			
			writer.println("<div class='column col-sm-12 col-xs-11' id='main'>");
			writer.println("<div class='padding'>");
			writer.println("<div class='row well'>");
			int j = 0;
			for(String x:ret.getPost_links())
			{
				if(j>=nono) break; //Display only testing set data
				writer.println("<div class='col-sm-6'>");
				writer.println("<div class='panel panel-default'>");
				writer.println("<div class='panel-heading'>");
				writer.println("<form action='" + x + "'>");
				writer.println("<button class='btn btn-primary pull-right' type='submit'>&nbsp; View Post &nbsp</button>");
				writer.println("</form>");    
				if(-5 <= predicted.get(j)-actual.get(j) & predicted.get(j)-actual.get(j)<=5)
				{
					writer.println("<h4 style=\"color:green\">");
					writer.println("Correct prediction");
				}
				else
				{
					writer.println("<h4 style=\"color:red\">");
					writer.println("Incorrect prediction");
				}
				writer.println("</h4>");
				writer.println("</div>");
				writer.println("<div class='panel-body'>");
				writer.println("<div class='list-group'>");
				writer.println("<p class='list-group-item'>Number of Predicted Likes : " + predicted.get(j) +"</p>");
				writer.println("<p class='list-group-item'>Number of Actual Likes : " + actual.get(j) +"</p>");
				writer.println("</div>");
				writer.println("</div>");
				writer.println("</div>");
				writer.println("</div>");
				j++;
			}
			writer.println("</div>");
			writer.println("</div>");
			writer.println("</div>");
			writer.println("<script src='//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js'></script>");
			writer.println("<script src='js/bootstrap.min.js'></script>");
			writer.println("<script src='js/scripts.js'></script>");
			writer.println("</body>");
			writer.println("</html>");
			//Clear graph and like histograms:
//			ParallelGraph.killIt();
//			WhoWillLike.killIt();
//			session.invalidate();
		}
		catch(Exception e)
		{
			writer.println("Error.");
		}
	}

}
