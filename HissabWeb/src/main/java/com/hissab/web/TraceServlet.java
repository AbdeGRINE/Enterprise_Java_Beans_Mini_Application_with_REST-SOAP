package com.hissab.web;

import com.hissab.ejb.HissabLocal;
import com.hissab.entity.Trace;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/traces")
public class TraceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private HissabLocal hissabEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Trace> traces = hissabEJB.listerTraces();
        request.setAttribute("traces", traces);

        request.getRequestDispatcher("traces.jsp").forward(request, response);
    }
}
