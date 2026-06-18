package com.hissab.ejb;

import com.hissab.entity.Trace;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.io.InputStream;
import java.util.List;

@Stateless
public class HissabEJB implements HissabLocal {

    @EJB
    private CalcLocal calcEJB;

    @EJB
    private TraceLocal traceEJB;

    @EJB
    private ExtractionLocal extractionEJB;

    @Override
    public double traiterExpression(String expression) {
        double resultat = calcEJB.calculer(expression);
        traceEJB.enregistrerTrace(expression, resultat);
        return resultat;
    }

    @Override
    public double traiterFichier(String nomFichier, InputStream inputStream) {
        String expression = extractionEJB.extraireExpression(nomFichier, inputStream);
        return traiterExpression(expression);
    }

    @Override
    public List<Trace> listerTraces() {
        return traceEJB.listerTraces();
    }
}
