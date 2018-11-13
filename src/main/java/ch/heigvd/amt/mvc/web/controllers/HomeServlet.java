package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.mvc.dao.UsersManagerLocal;
import ch.heigvd.amt.mvc.model.User;
import ch.heigvd.amt.mvc.model.UserApplication;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This a very simple controller. There is no service to invoke, no model to
 * prepare for the view. We simply delegate rendering of a static view to a
 * JSP page.
 * 
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class HomeServlet extends HttpServlet {

  @EJB
  UsersManagerLocal userManager;

  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      User user = (User) request.getSession().getAttribute("user");
      List<UserApplication> appList = userManager.getApplicationList(user.getEmail());
      request.setAttribute("app_list", appList);
      request.getRequestDispatcher("/WEB-INF/pages/apps.jsp").forward(request, response);
  }
}
