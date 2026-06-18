package com.hissab.rest;

import com.hissab.ejb.HissabLocal;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Stateless
@Path("/calc")
public class CalcRestService {

    @EJB
    private HissabLocal hissabEJB;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String calculer(@QueryParam("expression") String expression) {
        double resultat = hissabEJB.traiterExpression(expression);
        return String.valueOf(resultat);
    }
}