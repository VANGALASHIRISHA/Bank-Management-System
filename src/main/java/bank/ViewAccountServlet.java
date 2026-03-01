package bank;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

@WebServlet("/ViewAccountServlet")
public class ViewAccountServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","system","hello");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM bank_accounts");

            // Start HTML response
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta charset='UTF-8'>");
            pw.println("<title>View Accounts</title>");
            pw.println("<link rel='stylesheet' href='style.css'>");
            // Extra CSS for table styling
            pw.println("<style>");
            pw.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            pw.println("th, td { padding: 10px; text-align: center; border: 1px solid #0288d1; }");
            pw.println("th { background-color: #0288d1; color: white; }");
            pw.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            pw.println("</style>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<div class='container'>");
            pw.println("<h2>Bank Account Details</h2>");

            // Table of accounts
            pw.println("<table>");
            pw.println("<tr><th>ID</th><th>Name</th><th>Balance</th></tr>");
            boolean hasData = false;
            while(rs.next()) {
                hasData = true;
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getInt(3) + "</td>");
                pw.println("</tr>");
            }
            if(!hasData) {
                pw.println("<tr><td colspan='3'>No accounts found</td></tr>");
            }
            pw.println("</table>");

            pw.println("<a href='index.html'><button class='btn'>Go to Dashboard</button></a>");
            pw.println("</div>");
            pw.println("</body>");
            pw.println("</html>");

            con.close();

        } catch(Exception e) {
            e.printStackTrace();
            pw.println("<!DOCTYPE html>");
            pw.println("<html><body>");
            pw.println("<div class='container'>");
            pw.println("<h2 style='color:red'>Error Occurred</h2>");
            pw.println("<p>" + e.getMessage() + "</p>");
            pw.println("<a href='index.html'><button class='btn'>Go to Dashboard</button></a>");
            pw.println("</div>");
            pw.println("</body></html>");
        }
    }
}
