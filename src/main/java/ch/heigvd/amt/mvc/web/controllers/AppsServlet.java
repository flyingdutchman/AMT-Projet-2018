package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.jdbc.dao.UserApplicationManager;
import ch.heigvd.amt.jdbc.dao.UsersManager;
import ch.heigvd.amt.jdbc.model.User;
import ch.heigvd.amt.jdbc.model.UserApplication;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AppsServlet", urlPatterns = {"/apps"})
public class AppsServlet extends HttpServlet {

    @EJB
    UsersManager userManager;

    @EJB
    UserApplicationManager appManager;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer appId = (Integer)request.getAttribute("id");
        if (appId == null) {
            User user = (User) request.getSession().getAttribute("user");
            List<UserApplication> appList = userManager.getApplicationList(user.getEmail());
            request.setAttribute("app_list", appList);
            request.getRequestDispatcher("/WEB-INF/pages/apps.jsp").forward(request, response);
        } else {
            UserApplication application = appManager.getApplication(appId);
            request.setAttribute("application", application);
            request.getRequestDispatcher("/WEB-INF/pages/app.jsp").forward(request, response);
        }
    }
}
