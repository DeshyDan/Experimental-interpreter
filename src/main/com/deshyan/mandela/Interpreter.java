package com.deshyan.mandela;

import static com.deshyan.mandela.TokenType.*;

public class Interpreter {
    private final Lexer lexer;
    private Token currentToken;

    public Interpreter(String text) {
        this.lexer = new Lexer(text);

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
    private int term() {
        var token = currentToken;
        eat(INTEGER);
        return token.getValue();
    }

    /**
     * Return an INTEGER token value
     *
     * @return value of current token
     */
    private int factor() {
        var token = currentToken;
        eat(INTEGER);
        return token.getValue();
    }

    /**
     * Parses the text and evaluates it. Limited to INTEGER OP INTEGER and MINUS OP MINUS
     */
    public int expression() {
        currentToken = lexer.getNextToken();

        int result = term();

        while (currentToken.getTokenType() == MULTIPLY || currentToken.getTokenType() == DIVIDE) {
            var token = currentToken;
            if (token.getTokenType() == MULTIPLY) {
                eat(MULTIPLY);
                result *= factor();
            } else if (token.getTokenType() == DIVIDE) {
                eat(DIVIDE);
                result /= factor();
            }
        }
        //        while (currentToken.getTokenType() == PLUS || currentToken.getTokenType() == MINUS) {
        //            var token = currentToken;
        //
        //            if (token.getTokenType() == PLUS) {
        //                eat(PLUS);
        //                result += term();
        //            } else if (token.getTokenType() == MINUS) {
        //                eat(MINUS);
        //                result -= term();
        //            }
        //        }
        return result;
    }
}