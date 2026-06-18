package com.hissab.ejb;

import com.hissab.entity.Trace;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TraceEJB implements TraceLocal {

    @PersistenceContext(unitName = "HissabPU")
    private EntityManager em;

    @Override
    public void enregistrerTrace(String expression, double resultat) {
        Trace trace = new Trace(expression, resultat);
        em.persist(trace);
    }

    @Override
    public List<Trace> listerTraces() {
        return em.createQuery("SELECT t FROM Trace t ORDER BY t.dateTraitement DESC", Trace.class)
                 .getResultList();
    }
}
