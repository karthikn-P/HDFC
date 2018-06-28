

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
import javax.servlet.http.HttpSession;

public class DepositSrv extends HttpServlet{
	
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
		resp.setContentType("text/html");
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","Karthi29");
			PreparedStatement ps=conn.prepareStatement("select * from REGISTER where name=?");
			String na = req.getParameter("name");
			ps.setString(1,na);
			ResultSet rs=ps.executeQuery();
			rs.next();
			if(na.equals(rs.getString(1)))
			{
				DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","Karthi29");
				PreparedStatement stmt=con.prepareStatement("insert into Deposit values(?,?,?,?)");
				String name = req.getParameter("name");
				stmt.setString(1,name);
				stmt.setString(2, req.getParameter("Bank"));
				int anum=Integer.parseInt(req.getParameter("Acntnmbr"));
				int money=Integer.parseInt(req.getParameter("Money"));
				stmt.setInt(3, anum);
				stmt.setInt(4, money);
				stmt.executeUpdate();
			out.println("Deposited Successfully");
			out.println("<a href='DepositForm.html'>Click Here to go Deposit again</a>");
			}
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			out.println("<h4 style=\"color:red\">Enter the correct name</h4>"+e.getMessage());
			out.println("<a href='DepositForm.html'>Click Here to go Deposit again</a>");
			RequestDispatcher rd=req.getRequestDispatcher("Deposit.html");  
	        rd.include(req,resp);
			
		}
		
	}
}
