package com.deshyan.interpretor;

import com.deshyan.interpretor.abstractSyntaxTree.AbstractSyntaxTree;
import com.deshyan.interpretor.interpreter.Interpreter;
import com.deshyan.interpretor.lexer.Lexer;
import com.deshyan.interpretor.lexer.Token;
import com.deshyan.interpretor.parser.Parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
       String sourceCode = "" +
            "let x = 10;\n" +
            "func add(a, b) {\n" +
            "    return a + b;\n" +
            "}\n" +
            "let result = add(x, 5);\n";

        Lexer lexer = new Lexer(sourceCode);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        AbstractSyntaxTree ast = parser.parse();
        Interpreter interpreter = new Interpreter();
        Object result = interpreter.interpret(ast);
        System.out.println("Result: " + result);
    }
}
