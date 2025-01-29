package com.deshyan.mandela;

import com.deshyan.mandela.interpreter.Interpreter;
import com.deshyan.mandela.lexer.Lexer;
import com.deshyan.mandela.parser.Parser;

public class Main {
    public static void main(String[] args) {
        String code = "2+2";
        Lexer lexer = new Lexer(code);
        Parser parser = new Parser(lexer);
        var interpreter = new Interpreter(parser);
        // The parse gets token from the lexer and then returns the generated AST for the interpreter to traverse and interpret the input

        System.out.println(interpreter.interpret());
    }
}
