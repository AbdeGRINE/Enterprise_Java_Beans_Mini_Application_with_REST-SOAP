package com.hissab.soap;

import com.hissab.ejb.CalcLocal;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@Stateless
@WebService(
    name = "CalcSoapService",
    serviceName = "CalcSoapService",
    targetNamespace = "http://soap.hissab.com/"
)
public class CalcSoapService {

    @EJB
    private CalcLocal calcEJB;

    @WebMethod
    public double calculer(String expression) {
        return calcEJB.calculer(expression);
    }
}