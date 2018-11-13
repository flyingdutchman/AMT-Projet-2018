package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.jdbc.dao.BlacklistManager;
import ch.heigvd.amt.jdbc.dao.UsersManager;
import ch.heigvd.amt.jdbc.model.User;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet illustrates various aspects of the Servlet API.
 *
 * Firstly, notice that GET and POST requests are supported and that the same
 * code is invoked in both cases. In this application, /auth is invoked at two
 * occasions:
 *
 * 1) when the user fills out the login form and presses the "Login" button.
 * This sends a POST request, with the content of the input fields transmitted
 * in parameters (there is also a HIDDEN field named 'action' with a value of
 * 'login').
 *
 * 2) when the user clicks the "Logout" link in the navigation header. This
 * sends a GET request, with the value of the 'action' parameter set to 'logout'
 * in the query string (/auth?action=logout).
 *
 * Secondly, it shows how to put an object (in this case a String, but it could
 * be a POJO) in the session. This object is then available to servlets and JSPs
 * as long as the session is active.
 *
 * Thirdly, it shows how to terminate the session (which is important not only
 * to make sure that the user is logged out, but also to preserve resources on
 * the server). Even if sessions are automatically terminated after a
 * configurable idle time (typically 30 minutes), it is better to free allocated
 * memory as soon as possible.
 *
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class AuthenticationServlet extends HttpServlet {

  @EJB
  UsersManager userManager;

  @EJB
  BlacklistManager blacklistManager;

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    /*
     Get the parameter values, which have been transmitted either in the query string
     (for GET requests) or in the body (for POST requests).
     */
    String action = request.getParameter("action");
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    /*
     When the user is not logged in yet and tries to access /pages/xxx, then he
     is redirected to the login page by the security filter. The security filter
     stores the targer url (/pages/xxx) in the request context, so that we can
     send redirect the user to the desired location after successful authentication.
    
     If the user accessed /auth directly and there is no targetUrl, then we send him
     to the home page.
     */
    String targetUrl = (String) request.getAttribute("targetUrl");
    if (targetUrl == null) {
      targetUrl = "/home";
    }
    targetUrl = request.getContextPath() + targetUrl;

    //try to get the user to check his password, put an attribute error if
    User user = userManager.getUserByMail(email);
    request.getSession().setAttribute("user", user);
    boolean goodPassword = false;
    if (user == null) {
      request.setAttribute("error", "invalid password or mail");
    } else {
      goodPassword = user.getPassword().equals(password);
      if(!goodPassword) {
        request.setAttribute("error", "invalid password or mail");
      } else if (blacklistManager.isUserInBlacklist(user.getEmail())) {
        request.setAttribute("error", "you have been blacklisted");
      }
    }

    boolean errorOccured = request.getAttribute("error") != null;

    if ("login".equals(action) && goodPassword && !errorOccured) {
      request.getSession().setAttribute("principal", email);
      response.sendRedirect(targetUrl);
    } else if ("logout".equals(action) || !goodPassword || errorOccured) {
      request.getSession().invalidate();
      response.sendRedirect(request.getContextPath());
    } else {
      response.sendRedirect(targetUrl);
    }

  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
