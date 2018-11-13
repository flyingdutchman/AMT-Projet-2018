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

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @EJB
    UsersManager userManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getAttribute("resiteringUser");
        User createdUser = userManager.createAccount(user.getEmail(), user.getPassword(), user.getLastName(), user.getFirstName());
        request.setAttribute("createdUser", createdUser);
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }
}
