package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.mvc.services.UsersManagerLocal;
import ch.heigvd.amt.mvc.model.User;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

  @EJB
  UsersManagerLocal userManager;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User currentUser = (User) request.getSession().getAttribute("user");
    if (!userManager.isAdmin(currentUser)) {
      response.setStatus(403);
    } else {
      updateView(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if(request.getParameter("ban") != null) {
      String email = request.getParameter("ban");
      userManager.setUserIsBanned(email, true);
    } else if (request.getParameter("unban") != null) {
      String email = request.getParameter("unban");
      userManager.setUserIsBanned(email, false);
    } else if (request.getParameter("resetMail") != null) {
      System.out.println("RESET MAIL");
      String email = request.getParameter("resetMail");
      passwordResetMail(email);
    }

    updateView(request, response);
  }

  private void passwordResetMail(String email) {

    String subject = "Password Reset";
    String body = "An administrator has reset your password. Please log into your account to define a new one. Thank you !";

    String randomPassword = Long.toHexString(Double.doubleToLongBits(Math.random()));

    body += "\n\n Here is your temporary password : " + randomPassword;

    try {
      userManager.sendEmail(email, subject, body);
      System.out.println("GOT OUT");
      userManager.setUserIdPwdReset(email, true);
    } catch (MessagingException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  private void updateView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("users", userManager.getAllUsers().values());
    request.getRequestDispatcher("/WEB-INF/pages/admin.jsp").forward(request, response);
  }
}
