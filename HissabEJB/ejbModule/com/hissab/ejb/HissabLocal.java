package com.hissab.ejb;

import com.hissab.entity.Trace;
import jakarta.ejb.Local;
import java.io.InputStream;
import java.util.List;

@Local
public interface HissabLocal {
    double traiterExpression(String expression);
    double traiterFichier(String nomFichier, InputStream inputStream);
    List<Trace> listerTraces();
}
