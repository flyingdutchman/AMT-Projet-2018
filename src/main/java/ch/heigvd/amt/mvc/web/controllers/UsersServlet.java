package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.jdbc.dao.UsersManager;
import ch.heigvd.amt.jdbc.model.User;

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
    UsersManager userManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        if(!userManager.isAdmin(user)) {
            response.setStatus(403);
        } else {
            request.setAttribute("users", userManager.getUsers());
        }
        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request, response);
    }
}
