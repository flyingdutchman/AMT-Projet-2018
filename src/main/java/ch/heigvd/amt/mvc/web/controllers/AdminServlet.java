package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.mvc.dao.UsersManagerLocal;
import ch.heigvd.amt.mvc.model.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
      System.out.println("BAN");
      String email = request.getParameter("ban");
      userManager.setUserIsBanned(email, true);
    } else if (request.getParameter("unban") != null) {
      System.out.println("UNBAN");
      String email = request.getParameter("unban");
      userManager.setUserIsBanned(email, false);
    }

    updateView(request, response);
  }

  private void updateView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("users", userManager.getAllUsers().values());
    request.getRequestDispatcher("/WEB-INF/pages/admin.jsp").forward(request, response);
  }
}
