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

@WebServlet(name = "AppsServlet", urlPatterns = {"/apps"})
public class AppsServlet extends HttpServlet {

    @EJB
    UserApplicationManagerLocal appManager;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer appId = (Integer)request.getAttribute("id");
        if (appId != null) {
            UserApplication application = appManager.getApplication(appId);
            request.setAttribute("application", application);
            request.getRequestDispatcher("/WEB-INF/pages/apps.jsp").forward(request, response);
        } else {
            updateView(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer appId;
        Integer appIdEdit;
        try {
            appId = Integer.parseInt(request.getParameter("delete"));
        } catch (java.lang.NumberFormatException ex) {
            appId = null;
        }
        try {
            appIdEdit = Integer.parseInt(request.getParameter("edit"));
        } catch (java.lang.NumberFormatException ex) {
            appIdEdit = null;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (appIdEdit != null) {
            response.sendRedirect("./edit?id=" + appIdEdit);
        } else if (appId != null) {
            appManager.deleteApplication(appId, user.getEmail());
            updateView(request, response);
        } else {
            updateView(request, response);
        }
    }

    private void updateView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        ArrayList<UserApplication> appList = appManager.getApplicationList(user.getEmail());
        if(appList != null)
            request.setAttribute("app_list", appList);
        request.getRequestDispatcher("/WEB-INF/pages/apps.jsp").forward(request, response);
    }
}