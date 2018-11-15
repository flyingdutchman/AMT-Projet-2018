package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.mvc.dao.UserApplicationManager;
import ch.heigvd.amt.mvc.dao.UsersManager;
import ch.heigvd.amt.mvc.model.User;
import ch.heigvd.amt.mvc.model.UserApplication;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "AppsServlet", urlPatterns = {"/apps"})
public class AppsServlet extends HttpServlet {

    @EJB
    UsersManager userManager;

    @EJB
    UserApplicationManager appManager;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long appId = (Long)request.getAttribute("id");
        if (appId == null) {
            User user = (User) request.getSession().getAttribute("user");
            Map<String, UserApplication> appList = userManager.getApplicationList(user.getEmail());
            request.setAttribute("app_list", appList);
            request.getRequestDispatcher("/WEB-INF/pages/apps.jsp").forward(request, response);
        } else {
            UserApplication application = appManager.getApplication(appId);
            request.setAttribute("application", application);
            request.getRequestDispatcher("/WEB-INF/pages/app.jsp").forward(request, response);
        }
    }
}