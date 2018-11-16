package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.mvc.services.UserApplicationManagerLocal;
import ch.heigvd.amt.mvc.model.User;
import ch.heigvd.amt.mvc.model.UserApplication;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AppsEditServlet", urlPatterns = {"/edit"})
public class AppsEditServlet extends HttpServlet {

  @EJB
  UserApplicationManagerLocal appManager;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    User user = (User) request.getSession().getAttribute("user");
    Integer appId;
    try {
      appId = Integer.parseInt(request.getParameter("id"));
    } catch(java.lang.NumberFormatException ex) {
      appId = null;
    }
    request.setAttribute("appEdit", appManager.getApplication(appId));
    request.getRequestDispatcher("/WEB-INF/pages/appsEdit.jsp").forward(request, response);

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    String name = request.getParameter("name");
    String description = request.getParameter("description");
    Integer idApp = Integer.parseInt(request.getParameter("idApp"));

    appManager.updateApplication(idApp, name, description);

    User user = (User) request.getSession().getAttribute("user");
    ArrayList<UserApplication> appList = appManager.getApplicationList(user.getEmail());
    if(appList != null)
      request.setAttribute("app_list", appList);
    request.setAttribute("modified", "success");
    response.sendRedirect("./apps");
  }
}
