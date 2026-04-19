/*8a. Build a servlet program to  create a cookie to get your name through text box and press submit button( through HTML)  to display the message by greeting Welcome back your name ! , you have visited this page n times ( n = number of your visit )  along with the list of cookies and demonstrate the expiry of cookie also. 
*/
package com.cookie;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/VisitServlet")
public class VisitServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");

        int visitCount = 1;
        boolean userFound = false;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("username")) {
                    username = c.getValue();
                    userFound = true;
                }

                if (c.getName().equals("visitCount")) {
                    visitCount = Integer.parseInt(c.getValue());
                    visitCount++;
                }
            }
        }

        // Create / Update Cookies
        Cookie userCookie = new Cookie("username", username);
        Cookie visitCookie = new Cookie("visitCount", String.valueOf(visitCount));

        // Set expiry (Demo: 30 seconds)
        userCookie.setMaxAge(30);
        visitCookie.setMaxAge(30);

        response.addCookie(userCookie);
        response.addCookie(visitCookie);

        // Output
        out.println("<html><body>");
        out.println("<h2>Welcome back " + username + "!</h2>");
        out.println("<p>You have visited this page " + visitCount + " times.</p>");

        // Show all cookies
        out.println("<h3>List of Cookies:</h3>");

        if (cookies != null) {
            for (Cookie c : cookies) {
                out.println("Name: " + c.getName() + " | Value: " + c.getValue() + "<br>");
            }
        }

        out.println("<br><p><b>Note:</b> Cookies will expire in 30 seconds.</p>");
        out.println("<a href='index.html'>Visit Again</a>");

        out.println("</body></html>");
    }
}