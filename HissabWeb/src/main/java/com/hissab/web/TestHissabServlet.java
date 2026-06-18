package com.hissab.web;

import com.hissab.ejb.HissabLocal;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test")
public class TestHissabServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private HissabLocal hissabEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        double resultat = hissabEJB.traiterExpression("5 + 2 * 6 - 3");

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>Test HISSAB</h1>");
        response.getWriter().println("<p>Expression : 5 + 2 * 6 - 3</p>");
        response.getWriter().println("<p>Résultat : " + resultat + "</p>");
        response.getWriter().println("<p>Trace enregistrée dans la base.</p>");
    }
}
