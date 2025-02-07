package com.deshyan.interpreter.parser;

import com.deshyan.interpreter.abstractSyntaxTree.AbstractSyntaxTree;
import com.deshyan.interpreter.abstractSyntaxTree.BinaryOperator;
import com.deshyan.interpreter.abstractSyntaxTree.FunctionCall;
import com.deshyan.interpreter.abstractSyntaxTree.FunctionDefinition;
import com.deshyan.interpreter.abstractSyntaxTree.Number;
import com.deshyan.interpreter.abstractSyntaxTree.Program;
import com.deshyan.interpreter.abstractSyntaxTree.Return;
import com.deshyan.interpreter.abstractSyntaxTree.Variable;
import com.deshyan.interpreter.abstractSyntaxTree.VariableDeclaration;
import com.deshyan.interpreter.lexer.Token;
import com.deshyan.interpreter.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses a list of tokens into an abstract syntax tree.
 */
public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Parses the list of tokens into an abstract syntax tree.
     *
     * @return The abstract syntax tree representing the program.
     */
    public AbstractSyntaxTree parse() {
        return parseProgram();
    }

    /**
     * Parses the list of tokens into an abstract syntax tree.
     *
     * @return The abstract syntax tree representing the program.
     */
    private AbstractSyntaxTree parseProgram() {
        List<AbstractSyntaxTree> statements = new ArrayList<>();
        while (!match(TokenType.EOF)) {
            statements.add(parseStatement());
        }
        return new Program(statements);
    }

    /**
     * @return abstract syntax tree representing the statement.
     */
    private AbstractSyntaxTree parseStatement() {
        if (match(TokenType.KEYWORD, "let")) {
            return parseVariableDeclaration();
        } else if (match(TokenType.KEYWORD, "func")) {
            return parseFunctionDefinition();
        } else if (match(TokenType.KEYWORD, "return")) {
            return parseReturnStatement();
        } else {
            AbstractSyntaxTree expr = parseExpression();
            if (!match(TokenType.PUNCTUATION, "}")) {
                consume(TokenType.PUNCTUATION, ";");
            }
            return expr;
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

    private AbstractSyntaxTree parseReturnStatement() {
        consume(TokenType.KEYWORD, "return");
        AbstractSyntaxTree expr = parseExpression();
        consume(TokenType.PUNCTUATION, ";");
        return new Return(expr);
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

    /**
     * Parses a function call.
     *
     * @param name The name of the function to call.
     * @return
     */
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

    /**
     * Checks if the next token in the list matches the given type.
     *
     * @param type The type to check for.
     * @return true if the next token matches the given type, false otherwise.
     */
    private boolean match(TokenType type) {
        return pos < tokens.size() && tokens.get(pos).getType() == type;
    }

    /**
     * Consumes the next token in the list if it matches the given type and text.
     *
     * @param type The type of token to consume.
     * @param text The text of the token to consume.
     * @return The consumed token.
     */
    private Token consume(TokenType type, String text) {
        if (match(type, text)) {
            return tokens.get(pos++);
        }
        throw new RuntimeException("Expected " + type + " with text " + text + ", but found " + peek().getText());
    }

    /**
     * Consumes the next token in the list if it matches the given type.
     *
     * @param type The type of token to consume.
     * @return The consumed token.
     */
    private Token consume(TokenType type) {
        if (match(type)) {
            return tokens.get(pos++);
        }
        throw new RuntimeException("Expected " + type + ", but found " + peek().getText());
    }

    /**
     * Consumes the next token in the list.
     *
     * @return The consumed token.
     */
    private Token consume() {
        return tokens.get(pos++);
    }

    /**
     * @return The next token in the list.
     */
    private Token peek() {
        return tokens.get(pos);
    }
}