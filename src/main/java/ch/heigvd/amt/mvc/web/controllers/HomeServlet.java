package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.jdbc.dao.UsersManager;
import ch.heigvd.amt.jdbc.model.User;
import ch.heigvd.amt.jdbc.model.UserApplication;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This a very simple controller. There is no service to invoke, no model to
 * prepare for the view. We simply delegate rendering of a static view to a
 * JSP page.
 * 
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public class HomeServlet extends HttpServlet {

  @EJB
  UsersManager usersManager;

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
    // get the visuable app list depending on the rights of "user" attribute
    User user = (User)request.getSession().getAttribute("user");
    List<UserApplication> appList = new ArrayList<UserApplication>();
    if(usersManager.isAdmin(user)) {
      List<User> listUser = usersManager.getUsers();
      for(User us : listUser) {
        appList.addAll(usersManager.getApplicationList(us.getEmail()));
      }
    } else {
      appList.addAll(usersManager.getApplicationList(user.getEmail()));
    }
    request.setAttribute("app_list", appList);
    request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
  }

}
