package com.deshyan.mandela.parser;

import com.deshyan.mandela.lexer.Lexer;
import com.deshyan.mandela.lexer.Token;
import com.deshyan.mandela.lexer.TokenType;
import com.deshyan.mandela.abstractSyntaxTree.AbstractSyntaxTree;
import com.deshyan.mandela.abstractSyntaxTree.BinaryOperator;
import com.deshyan.mandela.abstractSyntaxTree.Number;
import com.deshyan.mandela.abstractSyntaxTree.UnaryOperator;

import static com.deshyan.mandela.lexer.TokenType.*;

/**
 * Parser to parse the tokens and build the AST
 */
public class Parser {
    private final Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        currentToken = lexer.getNextToken();
    }

    /**
     * Compares the current token type with the passed token type.
     * If they match then "eat" the current token and assign the next token to the currentToken
     */
    private void eat(TokenType tokenType) {
        if (currentToken.getTokenType().equals(tokenType)) {
            currentToken = lexer.getNextToken();
        } else {
            throw new IllegalArgumentException("Invalid syntax");
        }

    }
    /**
     * Return an INTEGER token value
     *
     * @return value of current token
     */
    private AbstractSyntaxTree term() {
        var node = factor();
        while (currentToken.getTokenType() == MULTIPLY || currentToken.getTokenType() == DIVIDE) {
            var token = currentToken;

            eat(token.getTokenType());

            node = new BinaryOperator(node, token, factor());
        }

        return node;


    }

    /**
     * Return an INTEGER token value
     *
     * @return value of current token
     */
    private AbstractSyntaxTree factor() {
        var token = currentToken;

        switch (token.getTokenType()) {
            case PLUS:
                eat(PLUS);
                return new UnaryOperator(token, factor());
            case MINUS:
                eat(MINUS);
                return new UnaryOperator(token, factor());
            case INTEGER:
                eat(INTEGER);
                return new Number(token);
            case LPAREN:
                eat(LPAREN);
                var node = expression();
                eat(RPAREN);
                return node;
            default:
                throw new IllegalArgumentException("Invalid syntax");
        }


    }

    /**
     * Parses the text and evaluates it.
     */
    public AbstractSyntaxTree expression() {

        var node = term();

        while (currentToken.getTokenType() == PLUS || currentToken.getTokenType() == MINUS) {
            var token = currentToken;

            eat(token.getTokenType());

            node = new BinaryOperator(node, token, term());
        }

        return node;
    }

    public AbstractSyntaxTree parse() {
        return expression();
    }
}