package com.hissab.ejb;

import jakarta.ejb.Local;
import java.io.InputStream;

@Local
public interface ExtractionLocal {
    String extraireExpression(String nomFichier, InputStream inputStream);
}
