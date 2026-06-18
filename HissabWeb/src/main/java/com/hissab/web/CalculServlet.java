package com.hissab.web;

import com.hissab.ejb.HissabLocal;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/calculer")
public class CalculServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private HissabLocal hissabEJB;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String expression = request.getParameter("expression");

        try {
            double resultat = hissabEJB.traiterExpression(expression);

            request.setAttribute("expression", expression);
            request.setAttribute("resultat", resultat);

        } catch (Exception e) {
            request.setAttribute("erreur", "Expression invalide : " + expression);
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
