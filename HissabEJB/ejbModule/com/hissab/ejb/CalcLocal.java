package com.hissab.ejb;

import jakarta.ejb.Local;

@Local
public interface CalcLocal {
    double calculer(String expression);
}