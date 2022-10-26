package com.epam.rd.autotasks.arithmeticexpressions;

import java.util.StringJoiner;

public class Expressions {

    public static Variable var(String name, int value) {
        return new Variable(name, value);
    }

    public static Expression val(int value) {
        return new Expression() {
            @Override
            public int evaluate() {
                return value;
            }

            @Override
            public String toExpressionString() {
                return value < 0 ? "(" + value + ")" : "" + value;
            }
        };
    }

    public static Expression sum(Expression... members) {
        return new Expression() {
            @Override
            public int evaluate() {
                int sum = 0;
                for(int i = 0; i < members.length; i++){
                    sum += members[i].evaluate();
                }
                return sum;
            }

            @Override
            public String toExpressionString() {
                String res = "(" + members[0].toExpressionString();
                for(int i = 1; i < members.length; i++)
                    res += " + " + members[i].toExpressionString();
                res += ")";
                return res;
            }
        };
    }

    public static Expression product(Expression... members) {
        return new Expression() {
            @Override
            public int evaluate() {
                int res = 1;
                for(int i = 0; i < members.length; i++){
                    res *= members[i].evaluate();
                }
                return res;
            }

            @Override
            public String toExpressionString() {
                String res = "(" + members[0].toExpressionString();
                for(int i = 1; i < members.length; i++)
                    res += " * " + members[i].toExpressionString();
                res += ")";
                return res;
            }
        };
    }

    public static Expression difference(Expression minuend, Expression subtrahend) {
        return new Expression() {
            @Override
            public int evaluate() {
                return minuend.evaluate() - subtrahend.evaluate();
            }

            @Override
            public String toExpressionString() {
                return "(" + minuend.toExpressionString() + " - " + subtrahend.toExpressionString() + ")";
            }
        };
    }

    public static Expression fraction(Expression dividend, Expression divisor) {
        return new Expression() {
            @Override
            public int evaluate() {
                if(divisor.evaluate() == 0)
                    throw new IllegalArgumentException();
                return (int) dividend.evaluate() / divisor.evaluate();
            }

            @Override
            public String toExpressionString() {
                return "(" + dividend.toExpressionString() + " / " + divisor.toExpressionString() + ")";
            }
        };
    }

}
