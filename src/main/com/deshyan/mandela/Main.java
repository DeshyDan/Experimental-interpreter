package com.deshyan.mandela;

public class Main {
    public static void main(String[] args) {
        String code = "2+2";
        Lexer lexer = new Lexer(code);
        Parser parser = new Parser(lexer);
        var interpreter = new Interpreter(parser);

        System.out.println(interpreter.interpret());
    }
}
