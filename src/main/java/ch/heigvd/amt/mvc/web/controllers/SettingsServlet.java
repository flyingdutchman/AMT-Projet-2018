package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.mvc.dao.UsersManager;
import ch.heigvd.amt.mvc.model.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SettingsServlet", urlPatterns = {"/settings"})
public class SettingsServlet extends HttpServlet {

    @EJB
    UsersManager userManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User newUser = (User)request.getAttribute("newUser");
        userManager.updateAccount(((User)request.getSession().getAttribute("user")).getEmail(), newUser.getEmail(), newUser.getPassword(), newUser.getLastName(), newUser.getFirstName());
        newUser = userManager.getUserByMail(newUser.getEmail());
        request.getSession().setAttribute("user", newUser);
        request.getRequestDispatcher("/WEB-INF/pages/settings.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/settings.jsp").forward(request, response);
    }
}
