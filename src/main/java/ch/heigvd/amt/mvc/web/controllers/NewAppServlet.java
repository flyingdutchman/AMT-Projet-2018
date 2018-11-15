package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.mvc.dao.UserApplicationManagerLocal;
import ch.heigvd.amt.mvc.dao.UsersManagerLocal;
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
import java.util.Map;

@WebServlet(name = "NewAppServlet", urlPatterns = {"/apps/new"})
public class NewAppServlet extends HttpServlet {

  @EJB
  UserApplicationManagerLocal appManager;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String appName = request.getParameter("appName");
    String appDescription = request.getParameter("appDescription");
    User user = (User) request.getSession().getAttribute("user");

    UserApplication appCreated = appManager.createApplication(user.getEmail(), appName, appDescription);
    ArrayList<UserApplication> appList = appManager.getApplicationList(user.getEmail());
    request.setAttribute("app_list", appList);
    request.getRequestDispatcher("/WEB-INF/pages/apps.jsp").forward(request, response);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.getRequestDispatcher("/WEB-INF/pages/newapp.jsp").forward(request, response);
  }
}
