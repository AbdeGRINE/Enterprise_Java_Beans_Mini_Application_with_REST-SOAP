package com.hissab.web;

import com.hissab.ejb.HissabLocal;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private HissabLocal hissabEJB;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part fichier = request.getPart("fichier");

        try {
            double resultat = hissabEJB.traiterFichier(
                    fichier.getSubmittedFileName(),
                    fichier.getInputStream()
            );

            request.setAttribute("expression", "Expression extraite du fichier : " + fichier.getSubmittedFileName());
            request.setAttribute("resultat", resultat);

        } catch (Exception e) {
            request.setAttribute("erreur", "Erreur pendant le traitement du fichier : " + e.getMessage());
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
