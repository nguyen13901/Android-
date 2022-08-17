package com.example.calculator;

import java.util.Arrays;
import java.util.Stack;

public class Handle {
    private boolean isError = false;
    private String varString[] = { "ans", "va", "vb", "vc", "vd", "ve", "vf" };
    private String constString[] = { "pi", "e" };
    public double var[] = new double[varString.length];
    private double cons[] = { Math.PI, Math.E };
    private boolean isDegOrRad = true;
    private int radix = 10, sizeRound = 10;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public int getSizeRound() {
        return sizeRound;
    }

    public void setSizeRound(int sizeRound) {
        this.sizeRound = sizeRound;
    }

    public int getRadix() {
        return radix;
    }

    public void setRadix(int radix) {
        this.radix = radix;
    }

    public boolean isDegOrRad() {
        return isDegOrRad;
    }

    public void setDegOrRad(boolean isDegOrRad) {
        this.isDegOrRad = isDegOrRad;
    }

    protected boolean isIntegerNumber(double num) {
        long a = (long) num;
        if (a == num) {
            return true;
        }
        return false;
    }

    private String myRound(double num, int size) {
        if (isIntegerNumber(num)) {
            return Long.toString((long) num);
        } else {
            int n = size - Long.toString((long) num).length();
            num = Math.round(num * Math.pow(10, n)) / Math.pow(10, n);
            if (isIntegerNumber(num)) {
                return Long.toString((long) num);
            } else {
                return Double.toString(num);
            }
        }
    }

    protected boolean isNumber(String s) {
        return true;
    }

    private boolean isNumber(char c) {
        String numberChar = ".0123456789abcdef";
        int index = numberChar.indexOf(c);
        if (radix == 10 && index >= 0 && index <= 10) {
            return true;
        }
        return false;
    }

    private double stringToNumber(String s) {
        int index = indexVar(s);
        if (index >= 0) {
            return var[index];
        }
        index = indexConst(s);
        if (index >= 0) {
            return cons[index];
        }
        if (s.charAt(s.length() - 1) == '.') {
            isError = true;
//				System.out.println("Error number have '.'");
            return -1;
        }
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            isError = true;
//				System.out.println("Error parse number");
        }
        return -1;
    }

    public String numberToString(double num, int radix, int size) {
        return myRound(num, size);
    }

    private int indexVar(String s) {
        for (int i = 0; i < varString.length; i++) {
            if (s.equals(varString[i])) {
                return i;
            }
        }
        return -1;
    }

    private int indexConst(String s) {
        for (int i = 0; i < constString.length; i++) {
            if (s.equals(constString[i])) {
                return i;
            }
        }
        return -1;
    }

    private boolean isVarOrConst(String s) {
        if (indexConst(s) >= 0 || indexVar(s) >= 0) {
            return true;
        }
        return false;
    }

    private boolean isOperator(String s) {
        String operator[] = { "+", "-", "*", "/", ")", "(", };
        Arrays.sort(operator);
        if (Arrays.binarySearch(operator, s) > -1) {
            return true;
        } else {
            return false;
        }
    }

    private int priority(String s) {
        int p = 1;
        if (s.equals("+") || s.equals("-")) {
            return p;
        }
        p++;
        if (s.equals("*") || s.equals("/")) {
            return p;
        }
        return 0;
    }

    private boolean isPostOperator(String s) {
        String postOperator[] = { "!" };
        for (int i = 0; i < postOperator.length; i++) {
            if (s.equals(postOperator[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isWord(char c1, char c2) {
        return false;
    }

    private boolean isWord(String s) {
        return false;
    }

    private String standardize(String s) {
        s = s.trim();
        s = s.replaceAll("\\s+", " ");
        return s;
    }

    private String[] trimString(String s) {
        String temp[] = s.split(" ");
        return temp;
    }

    private String standardizeMath(String[] s) { // chuan hoa bieu thuc
        String s1 = "";

        int open = 0, close = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i].equals("(")) {
                open++;
            } else if (s[i].equals(")")) {
                close++;
            }
        }

        for (int i = 0; i < s.length; i++) {
            if (i > 0 && isPostOperator(s[i - 1]) && isNumber(s[i])) {
                s1 = s1 + "* ";
            }
            if ((i == 0 || (i > 0 && !isNumber(s[i - 1])
                    && !s[i - 1].equals(")") && !isPostOperator(s[i - 1])))
                    && (s[i].equals("+"))
                    && (isNumber(s[i + 1]) || s[i + 1].equals("+"))) {
                continue;
            }
            if ((i == 0 || (i > 0 && !isNumber(s[i - 1])
                    && !s[i - 1].equals(")") && !isPostOperator(s[i - 1])))
                    && (s[i].equals("-"))
                    && (isNumber(s[i + 1]) || s[i + 1].equals("-"))) {
                s1 = s1 + "~ ";
            }
            else if (i > 0
                    && ((isNumber(s[i - 1]) || s[i - 1].equals(")")) && isVarOrConst(s[i]))) {
                s1 = s1 + "* " + s[i] + " ";
            } else {
                s1 = s1 + s[i] + " ";
            }
        }

        for (int i = 0; i < (open - close); i++) {
            s1 += ") ";
        }
        return s1;
    }

    private String processInput(String sMath) {
        sMath = sMath.toLowerCase();
        sMath = standardize(sMath); // chuan hoa bieu thuc
        String s = "", temp = "";
        for (int i = 0; i < sMath.length(); i++) {
            if (!isNumber(sMath.charAt(i))
                    || (i < sMath.length() - 1 && isWord(sMath.charAt(i),
                    sMath.charAt(i + 1)))) {
                s += " " + temp;
                temp = "" + sMath.charAt(i);
                if (isOperator(sMath.charAt(i) + "") && i < sMath.length() - 1
                        && !isWord(sMath.charAt(i), sMath.charAt(i + 1))) {
                    s += " " + temp;
                    temp = "";
                } else {
                    i++;
                    while (i < sMath.length()
                            && !isNumber(sMath.charAt(i))
                            && (!isOperator(sMath.charAt(i) + ""))
                            || (i < sMath.length() - 1 && isWord(
                            sMath.charAt(i - 1), sMath.charAt(i)))) {
                        temp += sMath.charAt(i);
                        i++;
                        if (isWord(temp)) {
                            s += " " + temp;
                            temp = "";
                            break;
                        }
                    }
                    i--;
                    s += " " + temp;
                    temp = "";
                }
            } else {
                temp = temp + sMath.charAt(i);
            }
        }
        s += " " + temp;
        s = standardize(s);
        s = standardizeMath(trimString(s));
        return s;
    }

    private String postFix(String math) {
        String[] elementMath = trimString(math);

        String s1 = "";
        Stack<String> S = new Stack<String>();
        for (int i = 0; i < elementMath.length; i++) {
            if (!isOperator(elementMath[i]))
            {
                s1 = s1 + elementMath[i] + " ";
            } else {
                if (elementMath[i].equals("(")) {
                    S.push(elementMath[i]);
                } else {
                    if (elementMath[i].equals(")")) {
                        String temp = "";
                        do {
                            temp = S.peek();
                            if (!temp.equals("(")) {
                                s1 = s1 + S.peek() + " ";
                            }
                            S.pop();
                        } while (!temp.equals("("));
                    } else {
                        while (!S.isEmpty() && priority(S.peek()) >= priority(elementMath[i])) {
                            s1 = s1 + S.pop() + " ";
                        }
                        S.push(elementMath[i]);
                    }
                }
            }
        }
        while (!S.isEmpty()) {
            s1 = s1 + S.pop() + " ";
        }
        return s1;
    }

    public Double valueMath(String math) {
        if (math == "" || math == null) {
            return Double.NaN;
        } else {
            math = processInput(math);
            math = postFix(math);
            String[] elementMath = trimString(math);
            Stack<Double> S = new Stack<Double>();
            double num = Double.NaN;
            double ans = Double.NaN;
            for (int i = 0; i < elementMath.length; i++) {
                if (!isOperator(elementMath[i])) {
                    S.push(stringToNumber(elementMath[i]));
                } else {
                    if (S.isEmpty()) {
                        System.out.println("Stack is empty ^^ ");
                        isError = true;
                        return Double.NaN;
                    }
                    double num1 = S.pop();
                    String ei = elementMath[i];

                    if (!S.empty()) {
                        double num2 = S.peek();
                        if (ei.equals("+")) {
                            num = num2 + num1;
                            S.pop();
                        } else if (ei.equals("-")) {
                            num = num2 - num1;
                            S.pop();
                        } else if (ei.equals("*")) {
                            num = num2 * num1;
                            S.pop();
                        } else if (ei.equals("/")) {
                            if (num1 != 0) {
                                num = num2 / num1;
                            } else {
                                isError = true;
                                return Double.NaN;
                            }
                            S.pop();
                        }
                    } else {
//							System.out.println("Error stack empty");
                        isError = true;
                        return Double.NaN;
                    }
                    S.push(num);
                }
            }
            ans = S.pop();
            return ans;
        }
    }
}
