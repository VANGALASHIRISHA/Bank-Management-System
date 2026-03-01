package bank;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/AddAccountServlet")
public class AddAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int balance = Integer.parseInt(request.getParameter("balance"));

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "system", "hello");

            PreparedStatement pst = con.prepareStatement("INSERT INTO bank_accounts VALUES (?, ?, ?)");
            pst.setInt(1, id);
            pst.setString(2, name);
            pst.setInt(3, balance);
            pst.executeUpdate();

            // HTML response with modern styling
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta charset='UTF-8'>");
            pw.println("<title>Account Added</title>");
            pw.println("<link rel='stylesheet' href='style.css'>"); // same CSS
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<div class='container'>");
            pw.println("<h2>Account Added Successfully!</h2>");
            pw.println("<p>Account ID: " + id + "<br>");
            pw.println("Account Holder: " + name + "<br>");
            pw.println("Balance: " + balance + "</p>");
            pw.println("<a href='index.html'><button class='btn'>Go to Dashboard</button></a>");
            pw.println("</div>");
            pw.println("</body>");
            pw.println("</html>");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<p style='color:red'>Error: " + e.getMessage() + "</p>");
        }
    }
}
