package ch.heigvd.amt.mvc.web.controllers;

import ch.heigvd.amt.mvc.dao.BlacklistManager;
import ch.heigvd.amt.mvc.model.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BlacklistServlet", urlPatterns = {"/blacklist"})
public class BlacklistServlet extends HttpServlet {

    @EJB
    BlacklistManager blacklistManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getAttribute("blacklistingUsed");
        blacklistManager.setUserIntoBlacklist(user.getEmail());
        request.getRequestDispatcher("/WEB-INF/pages/blacklist.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/blacklist.jsp").forward(request, response);
    }
}
