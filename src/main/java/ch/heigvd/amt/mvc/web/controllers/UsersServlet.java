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

@WebServlet(name = "UsersServlet", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {

  @EJB
  UsersManagerLocal userManager;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User currentUser = (User) request.getSession().getAttribute("user");
    if (!userManager.isAdmin(currentUser)) {
      response.setStatus(403);
    } else {
      request.setAttribute("users", userManager.getAllUsers().values());
    }
    request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
  }
}
