import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginSrv extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	Process(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	Process(req, resp);
	}
	public void Process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter out=resp.getWriter();
		try
		{
		int id = Integer.parseInt(req.getParameter("userid"));
        String pass = req.getParameter("pwd");
        String b=TrippleDes.encrypt(pass, "user");
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());  
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","Karthi29");  
		PreparedStatement ps=con.prepareStatement("select * from REGISTER where id=? and pass=?");
		resp.setContentType("text/html");
		ps.setInt(1,id);
		ps.setString(2,b);
		ResultSet rs=ps.executeQuery();
		rs.next();
		if(id==rs.getInt(2)&&b.equals(rs.getString(3)))
		{
			out.println("<a href='./logout align='right'>Logout</a>");
			out.println("<h1>Welcome "+rs.getString(1)+"</h1>");
			out.println("<h1> You are logged in Successfully! </h1>");
			out.println("<h3>User ID : "+rs.getInt(2)+" </h3>");
			out.println("<h3>Emailid :"+rs.getString(4)+" </h3>");
			out.println("<a href='DepositForm.html'>Click Here to go to Deposit page</a>");
		}
		else
		{
			out.println("Please enter the correct password");
			RequestDispatcher rd=req.getRequestDispatcher("Login.html");  
	        rd.include(req,resp);
		}
		}
		catch (Exception e) {
			 out.println("<h4 style=\"color:red\">Username or Password Error </h4>");
				RequestDispatcher rd=req.getRequestDispatcher("Login.html");  
		        rd.include(req,resp);
		}
}
}
