import java.io.IOException;  
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.*;  
  
public class Authentication implements Filter{  
  
public void init(FilterConfig arg0) throws ServletException {}  
      
public void doFilter(ServletRequest req, ServletResponse resp,  
        FilterChain chain) throws IOException, ServletException {  
     try {  
    	 int id = Integer.parseInt(req.getParameter("userid"));
    	 String password=req.getParameter("pwd");
    	
    	 DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());  
 		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","Karthi29");  
 		PreparedStatement ps=con.prepareStatement("select * from REGISTER where id=? and pass=?");
 		
 		resp.setContentType("text/html");
 		 ps.setInt(1,id);
 		ps.setString(2,password);
 		ResultSet rs=ps.executeQuery();
 		rs.next();
    PrintWriter out=resp.getWriter();  
          
    
    
    if(id==rs.getInt(2)&&password.equals(rs.getString(3)))
    {
    chain.doFilter(req, resp);//sends request to next resource  
    }  
    else{  
    out.print("username or password error!");  
    RequestDispatcher rd=req.getRequestDispatcher("index.html");  
    rd.include(req, resp);  
    }
     }
     catch (Exception e) {
 		// TODO: handle exception
 	}  
          
}  
    public void destroy() {}  
  
} 