package com.hissab.ejb;

import jakarta.ejb.Stateless;
import java.util.Stack;

@Stateless
public class CalcEJB implements CalcLocal {

    @Override
    public double calculer(String expression) {
        try {
            expression = expression.trim()
                                   .replace("×", "*")
                                   .replace("÷", "/")
                                   .replace("−", "-");
            return evaluer(expression);
        } catch (Exception e) {
            throw new RuntimeException("Expression invalide : " + expression);
        }
    }

    private double evaluer(String expr) {
        Stack<Double> valeurs = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int i = 0;

        while (i < expr.length()) {
            char c = expr.charAt(i);

            // Ignorer les espaces
            if (c == ' ') { i++; continue; }

            // Nombre (entier ou décimal)
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() &&
                       (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i++));
                }
                valeurs.push(Double.parseDouble(sb.toString()));
                continue;
            }

            // Parenthèse ouvrante
            if (c == '(') { ops.push(c); i++; continue; }

            // Parenthèse fermante
            if (c == ')') {
                while (ops.peek() != '(')
                    valeurs.push(appliquer(ops.pop(), valeurs.pop(), valeurs.pop()));
                ops.pop();
                i++; continue;
            }

            // Opérateur
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!ops.isEmpty() && priorite(ops.peek()) >= priorite(c))
                    valeurs.push(appliquer(ops.pop(), valeurs.pop(), valeurs.pop()));
                ops.push(c);
                i++; continue;
            }
            i++;
        }

        while (!ops.isEmpty())
            valeurs.push(appliquer(ops.pop(), valeurs.pop(), valeurs.pop()));

        return valeurs.pop();
    }

    private int priorite(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }

    private double appliquer(char op, double b, double a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division par zéro");
                return a / b;
        }
        return 0;
    }
}