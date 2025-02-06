package com.deshyan.interpreter.parser;

import com.deshyan.interpreter.abstractSyntaxTree.AbstractSyntaxTree;
import com.deshyan.interpreter.abstractSyntaxTree.BinaryOperator;
import com.deshyan.interpreter.abstractSyntaxTree.FunctionCall;
import com.deshyan.interpreter.abstractSyntaxTree.FunctionDefinition;
import com.deshyan.interpreter.abstractSyntaxTree.Number;
import com.deshyan.interpreter.abstractSyntaxTree.Program;
import com.deshyan.interpreter.abstractSyntaxTree.Variable;
import com.deshyan.interpreter.abstractSyntaxTree.VariableDeclaration;
import com.deshyan.interpreter.lexer.Token;
import com.deshyan.interpreter.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;


public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public AbstractSyntaxTree parse() {
        return parseProgram();
    }

    private AbstractSyntaxTree parseProgram() {
        List<AbstractSyntaxTree> statements = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            statements.add(parseStatement());
        }
        return new Program(statements);
    }

    private AbstractSyntaxTree parseStatement() {
        if (match(TokenType.KEYWORD, "let")) {
            return parseVariableDeclaration();
        } else if (match(TokenType.KEYWORD, "func")) {
            return parseFunctionDefinition();
        } else {
            return parseExpression();
        }
    }

    private AbstractSyntaxTree parseVariableDeclaration() {
        consume(TokenType.KEYWORD, "let");
        Token name = consume(TokenType.IDENTIFIER);
        consume(TokenType.PUNCTUATION, "=");
        AbstractSyntaxTree value = parseExpression();
        consume(TokenType.PUNCTUATION, ";");
        return new VariableDeclaration(name.getText(), value);
    }

    private AbstractSyntaxTree parseFunctionDefinition() {
        consume(TokenType.KEYWORD, "func");
        Token name = consume(TokenType.IDENTIFIER);
        consume(TokenType.PUNCTUATION, "(");
        List<String> parameters = new ArrayList<>();
        while (!match(TokenType.PUNCTUATION, ")")) {
            parameters.add(consume(TokenType.IDENTIFIER).getText());
            if (!match(TokenType.PUNCTUATION, ")")) {
                consume(TokenType.PUNCTUATION, ",");
            }
        }
        consume(TokenType.PUNCTUATION, ")");
        consume(TokenType.PUNCTUATION, "{");
        List<AbstractSyntaxTree> body = new ArrayList<>();
        while (!match(TokenType.PUNCTUATION, "}")) {
            body.add(parseStatement());
        }
        consume(TokenType.PUNCTUATION, "}");
        return new FunctionDefinition(name.getText(), parameters, body);
    }


    private AbstractSyntaxTree parseExpression() {
        return parseAdditiveExpression();
    }

    private AbstractSyntaxTree parseAdditiveExpression() {
        AbstractSyntaxTree left = parseMultiplicativeExpression();
        while (match(TokenType.OPERATOR, "+") || match(TokenType.OPERATOR, "-")) {
            Token operator = consume();
            AbstractSyntaxTree right = parseMultiplicativeExpression();
            left = new BinaryOperator(left, operator, right);
        }
        return left;
    }

    private AbstractSyntaxTree parseMultiplicativeExpression() {
        AbstractSyntaxTree left = parsePrimaryExpression();
        while (match(TokenType.OPERATOR, "*") || match(TokenType.OPERATOR, "/")) {
            Token operator = consume();
            AbstractSyntaxTree right = parsePrimaryExpression();
            left = new BinaryOperator(left, operator, right);
        }
        return left;
    }

    private AbstractSyntaxTree parsePrimaryExpression() {
        if (match(TokenType.NUMBER)) {
            return new Number(Integer.parseInt(consume().getText()));
        } else if (match(TokenType.IDENTIFIER)) {
            Token identifier = consume();
            if (match(TokenType.PUNCTUATION, "(")) {
                return parseFunctionCall(identifier.getText());
            }
            return new Variable(identifier.getText());
        } else if (match(TokenType.PUNCTUATION, "(")) {
            consume(TokenType.PUNCTUATION, "(");
            AbstractSyntaxTree expr = parseExpression();
            consume(TokenType.PUNCTUATION, ")");
            return expr;
        }
        throw new RuntimeException("Unexpected token: " + peek().getText());
    }

    private AbstractSyntaxTree parseFunctionCall(String name) {
        consume(TokenType.PUNCTUATION, "(");
        List<AbstractSyntaxTree> arguments = new ArrayList<>();
        while (!match(TokenType.PUNCTUATION, ")")) {
            arguments.add(parseExpression());
            if (!match(TokenType.PUNCTUATION, ")")) {
                consume(TokenType.PUNCTUATION, ",");
            }
        }
        consume(TokenType.PUNCTUATION, ")");
        return new FunctionCall(name, arguments);
    }

    private boolean match(TokenType type, String text) {
        return pos < tokens.size() && tokens.get(pos).getType() == type && tokens.get(pos).getText().equals(text);
    }

    private boolean match(TokenType type) {
        return pos < tokens.size() && tokens.get(pos).getType() == type;
    }

    private Token consume(TokenType type, String text) {
        if (match(type, text)) {
            return tokens.get(pos++);
        }
        throw new RuntimeException("Expected " + type + " with text " + text + ", but found " + peek().getText());
    }
    private Token consume(TokenType type) {
        if (match(type)) {
            return tokens.get(pos++);
        }
        throw new RuntimeException("Expected " + type + ", but found " + peek().getText());
    }

    private Token consume() {
        return tokens.get(pos++);
    }

    private Token peek() {
        return tokens.get(pos);
    }
}