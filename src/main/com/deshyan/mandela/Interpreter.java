package com.deshyan.mandela;

import static com.deshyan.mandela.TokenType.*;

public class Interpreter {
    private final char[] text;
    private int tokenizerIndex;
    private Token currentToken;

    public Interpreter(String text) {
        this.text = text.toCharArray();
        this.tokenizerIndex = 0;
    }

    /**
     * Responsible for breaking a sentence apart into tokens. This is also referred as lexical analysis/tokenization.
     *
     * @return Token with extracted token it values
     */
    private Token getNextToken() {

        // If we reach end of file, there are no more token to consume
        if (tokenizerIndex > text.length - 1) {
            return new Token(EOF, null);
        }

        char currentChar = text[tokenizerIndex];

        if (Character.isDigit(currentChar)) {
            tokenizerIndex++;
            return new Token(INTEGER, Character.getNumericValue(currentChar));
        } else if (currentChar == '+') {
            tokenizerIndex++;
            return new Token(PLUS, currentChar);
        } else {
            throw new IllegalArgumentException("Token not supported");
        }
    }

    /**
     * Compares the current token type with the passed token type.
     * If they match then "eat" the current token and assign the next token to the currentToken
     */
    private void eat(TokenType tokenType) {
        if (currentToken.getTokenType().equals(tokenType)) {
            currentToken = getNextToken();
        }

    }

    /**
     * Analyzes an expression. Limit to INTEGER OP INTEGER
     */
    public int expression() {
        currentToken = getNextToken();

        Token left = currentToken;
        eat(INTEGER);

        var operation = currentToken;
        eat(PLUS);

        Token right = currentToken;
        eat(INTEGER);


        int leftResult = left.getValue();
        int rightResult = right.getValue();

        return leftResult + rightResult;
    }
}