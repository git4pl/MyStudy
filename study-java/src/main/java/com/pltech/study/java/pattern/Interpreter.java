package com.pltech.study.java.pattern;

public class Interpreter {
    public static void main(String[] args) {

        Expression person1 = new TerminalExpression("mick");

        Expression person2 = new TerminalExpression("mia");

        Expression isSingle = new OrExpression(person1, person2);

        Expression spike = new TerminalExpression("spike");

        Expression mock = new TerminalExpression("mock");

        Expression isCommitted = new AndExpression(spike, mock);

        System.out.println(isSingle.interpreter("mick"));

        System.out.println(isSingle.interpreter("mia"));

        System.out.println(isSingle.interpreter("max"));

        System.out.println(isCommitted.interpreter("mock, spike"));

        System.out.println(isCommitted.interpreter("Single, mock"));
    }

}

interface Expression {
    boolean interpreter(String con);
}

class TerminalExpression implements Expression {

    String data;

    public TerminalExpression(String data) {
        this.data = data;
    }

    @Override
    public boolean interpreter(String con) {
        return con.contains(data);
    }

}

class OrExpression implements Expression {

    Expression expr1;

    Expression expr2;

    public OrExpression(Expression expr1, Expression expr2) {

        this.expr1 = expr1;

        this.expr2 = expr2;

    }

    public boolean interpreter(String con) {
        return expr1.interpreter(con) || expr2.interpreter(con);
    }

}

class AndExpression implements Expression {

    Expression expr1;

    Expression expr2;

    public AndExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public boolean interpreter(String con) {
        return expr1.interpreter(con) && expr2.interpreter(con);
    }

}

