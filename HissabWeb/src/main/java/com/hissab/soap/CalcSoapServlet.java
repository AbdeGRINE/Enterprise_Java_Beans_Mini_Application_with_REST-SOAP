package com.hissab.soap;

import com.hissab.ejb.HissabLocal;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/soap/calc")
public class CalcSoapServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private HissabLocal hissabEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        response.getWriter().write(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<wsdl:definitions xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" " +
            "xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" " +
            "xmlns:tns=\"http://soap.hissab.com/\" " +
            "targetNamespace=\"http://soap.hissab.com/\">" +
            "<wsdl:service name=\"CalcSoapService\">" +
            "<wsdl:port name=\"CalcSoapPort\" binding=\"tns:CalcSoapBinding\">" +
            "<soap:address location=\"http://localhost:8080/HissabWeb/soap/calc\"/>" +
            "</wsdl:port>" +
            "</wsdl:service>" +
            "</wsdl:definitions>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder xml = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;

        while ((line = reader.readLine()) != null) {
            xml.append(line);
        }

        String expression = extraireExpression(xml.toString());
        double resultat = hissabEJB.traiterExpression(expression);

        String soapResponse =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<soapenv:Body>" +
            "<calculerResponse xmlns=\"http://soap.hissab.com/\">" +
            "<return>" + resultat + "</return>" +
            "</calculerResponse>" +
            "</soapenv:Body>" +
            "</soapenv:Envelope>";

        response.setContentType("text/xml;charset=UTF-8");
        response.getWriter().write(soapResponse);
    }

    private String extraireExpression(String xml) {
        int start = xml.indexOf("<expression>");
        int end = xml.indexOf("</expression>");

        if (start != -1 && end != -1) {
            return xml.substring(start + "<expression>".length(), end).trim();
        }

        start = xml.indexOf("<arg0>");
        end = xml.indexOf("</arg0>");

        if (start != -1 && end != -1) {
            return xml.substring(start + "<arg0>".length(), end).trim();
        }

        throw new RuntimeException("Expression introuvable dans la requête SOAP");
    }
}