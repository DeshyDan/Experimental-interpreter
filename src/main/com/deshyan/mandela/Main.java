package com.deshyan.mandela;

public class Main {
    public static void main(String[] args) {
        String code = "10*2";
        var interpreter = new Interpreter(code);

        System.out.println(interpreter.expression());
    }
}
