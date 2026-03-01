package bank;

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

@WebServlet("/UpdateAccountServlet")
public class UpdateAccountServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int balance = Integer.parseInt(request.getParameter("balance"));

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "hello");

            PreparedStatement pst = con.prepareStatement(
                    "UPDATE bank_accounts SET name = ?, balance = ? WHERE Account_id = ?");

            pst.setString(1, name);
            pst.setInt(2, balance);
            pst.setInt(3, id);

            int rows = pst.executeUpdate();

            // Start HTML response with styling
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta charset='UTF-8'>");
            pw.println("<title>Update Account</title>");
            pw.println("<link rel='stylesheet' href='style.css'>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<div class='container'>");

            if (rows > 0) {
                pw.println("<h2>Account Updated Successfully!</h2>");
                pw.println("<p>Account ID: " + id + "<br>");
                pw.println("New Name: " + name + "<br>");
                pw.println("New Balance: " + balance + "</p>");
            } else {
                pw.println("<h2>No Account Found</h2>");
                pw.println("<p>No account exists with ID: " + id + ".</p>");
            }

            pw.println("<a href='index.html'><button class='btn'>Go to Dashboard</button></a>");
            pw.println("</div>");
            pw.println("</body>");
            pw.println("</html>");

            con.close();

        } catch (Exception e) {
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
