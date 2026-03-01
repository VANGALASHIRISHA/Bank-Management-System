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

@WebServlet("/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String idstr = request.getParameter("id");
        int accountID = Integer.parseInt(idstr);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "hello");

            PreparedStatement pst = con.prepareStatement("DELETE FROM bank_accounts WHERE Account_id=?");
            pst.setInt(1, accountID);
            int rows = pst.executeUpdate();

            // HTML response with modern styling
            pw.println("<!DOCTYPE html>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta charset='UTF-8'>");
            pw.println("<title>Delete Account</title>");
            pw.println("<link rel='stylesheet' href='style.css'>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<div class='container'>");

            if (rows > 0) {
                pw.println("<h2>Account Deleted Successfully!</h2>");
                pw.println("<p>Account ID: " + accountID + " has been removed.</p>");
            } else {
                pw.println("<h2>No Account Found</h2>");
                pw.println("<p>No account exists with ID: " + accountID + ".</p>");
            }

            pw.println("<a href='index.html'><button class='btn delete-btn'>Go to Dashboard</button></a>");
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
