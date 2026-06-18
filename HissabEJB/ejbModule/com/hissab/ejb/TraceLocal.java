package com.hissab.ejb;

import com.hissab.entity.Trace;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface TraceLocal {
    void enregistrerTrace(String expression, double resultat);
    List<Trace> listerTraces();
}
