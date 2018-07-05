import java.io.*;  
import java.sql.*;  
import javax.servlet.ServletException;  
import javax.servlet.http.*;  
  
public class Register extends HttpServlet {  
	public void doPost(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException 
		
	{
  
  try
		{  
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());  
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","Karthi29");  
  
			PreparedStatement ps=con.prepareStatement("insert into REGISTER values(?,?,?,?,?)");
			resp.setContentType("text/html");  
			PrintWriter out = resp.getWriter();  
	          
			String n=req.getParameter("userName");
			int a=Integer.parseInt(req.getParameter("userid"));
			String p=req.getParameter("userPass");
			String b=TrippleDes.encrypt(p, "user");
			
			String e=req.getParameter("userEmail");  
			String c=req.getParameter("userCountry");
	  
			ps.setString(1,n);
			ps.setInt(2,a);
			ps.setString(3,b);  
			ps.setString(4,e);  
			ps.setString(5,c);  
			          
			ps.executeUpdate();    
			out.println("You are successfully registered..."+"<div class=\"row\">\r\n" + 
					"<div class=\"col-md-12\">\r\n" + 
					"<div class=\"view\" style=\"background-image: url('background.jpg'); background-repeat: no-repeat;  background-position: center center; height:100%\">\r\n" + 
					"<div class=\"mask rgba-gradient d-flex justify-content-center align-items-center\">\r\n"+
					"<a href='Login.html'>Click Here to go to Login page</a>");
		}
			catch (Exception e2) {System.out.println(e2);
		}  
	}  
}  