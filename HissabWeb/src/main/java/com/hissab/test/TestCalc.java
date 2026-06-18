package com.hissab.test;

import com.hissab.ejb.CalcEJB;

public class TestCalc {
    public static void main(String[] args) {
        CalcEJB calc = new CalcEJB();
        System.out.println(calc.calculer("5 + 2 * 6 - 3")); // attendu : 14.0
        System.out.println(calc.calculer("(5 + 2) * 6 - 3")); // attendu : 39.0
        System.out.println(calc.calculer("10 / 2 + 3"));      // attendu : 8.0
    }
}