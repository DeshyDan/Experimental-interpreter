package com.deshyan.mandela;

public class Main {
    public static void main(String[] args) {
        String code = "2 +2-42+45-";
        var interpreter = new Interpreter(code);

        System.out.println(interpreter.expression());
    }
}
