package com.hissab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@WebServlet("/clientCalc")
public class ClientCalcServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String expression = request.getParameter("expression");
        String typeService = request.getParameter("typeService");

        try {
            String resultat;

            if ("REST".equals(typeService)) {
                resultat = appelerRest(expression);
            } else {
                resultat = appelerSoap(expression);
            }

            request.setAttribute("expression", expression);
            request.setAttribute("typeService", typeService);
            request.setAttribute("resultat", resultat);

        } catch (Exception e) {
            request.setAttribute("erreur", "Erreur : " + e.getMessage());
        }

        request.getRequestDispatcher("client.jsp").forward(request, response);
    }

    private String appelerRest(String expression) throws Exception {
        String url = "http://localhost:8080/HissabWeb/api/calc?expression="
                + URLEncoder.encode(expression, "UTF-8");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new URL(url).openStream(), "UTF-8"));

        return reader.readLine();
    }

    private String appelerSoap(String expression) throws Exception {
        String soapXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<calculer xmlns=\"http://soap.hissab.com/\">" +
                "<expression>" + expression + "</expression>" +
                "</calculer>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";

        URL url = new URL("http://localhost:8080/HissabWeb/soap/calc");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        os.write(soapXml.getBytes("UTF-8"));
        os.close();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuilder xml = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            xml.append(line);
        }

        String reponse = xml.toString();

        int start = reponse.indexOf("<return>");
        int end = reponse.indexOf("</return>");

        if (start != -1 && end != -1) {
            return reponse.substring(start + "<return>".length(), end);
        }

        return reponse;
    }
}