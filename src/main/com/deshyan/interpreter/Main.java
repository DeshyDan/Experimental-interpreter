package com.deshyan.interpreter;

import com.deshyan.interpreter.abstractSyntaxTree.AbstractSyntaxTree;
import com.deshyan.interpreter.interpreter.Interpreter;
import com.deshyan.interpreter.lexer.Lexer;
import com.deshyan.interpreter.lexer.Token;
import com.deshyan.interpreter.parser.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    final static String SAMPLE_FILE = "src/main/com/deshyan/interpreter/sample.txt";

    public static void main(String[] args) throws IOException {

        String sourceCode = Files.readString(Paths.get(SAMPLE_FILE));

        Lexer lexer = new Lexer(sourceCode);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        AbstractSyntaxTree ast = parser.parse();
        Interpreter interpreter = new Interpreter();
        Object result = interpreter.interpret(ast);
        System.out.println("Result: " + result);
    }
}
