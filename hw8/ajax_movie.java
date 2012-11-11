import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.net.*;




public class ajax_movie extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

		response.setContentType("text/plain");
		String zipCode = request.getParameter("zipCode");
		PrintWriter out = response.getWriter();
        
        String urlString = "http://cs-server.usc.edu:26378/getXml.php?zipCode="+zipCode;

        URL url = new URL(urlString);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setAllowUserInteraction(false);
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

		String inputLine;
        String totalLine = new String("");
		int count = 0;
        while ((inputLine = in.readLine()) != null)
		{
                totalLine=totalLine+inputLine;    
		}
		out.println(totalLine);
        in.close();
        
	
        
    }

}