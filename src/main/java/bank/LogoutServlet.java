package bank;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Modern styled HTML response
        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<meta charset='UTF-8'>");
        pw.println("<title>Logged Out</title>");
        pw.println("<link rel='stylesheet' href='style.css'>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<div class='container'>");
        pw.println("<h2>You have been logged out successfully!</h2>");
        pw.println("<p>Thank you for using our banking system.</p>");
        pw.println("<a href='index.html'><button class='btn'>Go to Home Page</button></a>");
        pw.println("</div>");
        pw.println("</body>");
        pw.println("</html>");
    }
}
