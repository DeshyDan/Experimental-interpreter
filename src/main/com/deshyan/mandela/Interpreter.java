package com.deshyan.mandela;

import static com.deshyan.mandela.TokenType.*;

public class Interpreter {
    private final char[] text;
    private int tokenizerIndex;
    private Token currentToken;
    private Character currentChar;

    public Interpreter(String text) {
        this.text = text.toCharArray();
        this.tokenizerIndex = 0;
        setCurrentChar(tokenizerIndex);
    }

    /**
     * Advances the tokenizerIndex to the next character in the text and sets the currentToken to current character
     */
    private void advance() {
        tokenizerIndex++;
        setCurrentChar(tokenizerIndex);
    }

    /**
     * Advances tokenizerIndex if a whitespace is detected
     */
    private void skipWhitespace() {
        while (currentChar != null &&
                currentChar == ' ') {
            advance();
        }
    }

    private void setCurrentChar(int index) {
        if (tokenizerIndex > text.length - 1) {
            currentChar = null;
        } else {
            currentChar = text[index];
        }
    }

    /**
     * Constructs a multi-digit integer by combining the token between non integers
     *
     * @return int
     */
    private int constructIntegerFromTokens() {
        StringBuilder result = new StringBuilder();
        while (currentChar != null && Character.isDigit(currentChar)) {

            result.append(currentChar);
            advance();
        }
        return Integer.parseInt(result.toString());
    }

    /**
     * Responsible for breaking a sentence apart into tokens. This is also referred as lexical analysis/tokenization.
     *
     * @return Token with extracted token it values
     */
    private Token getNextToken() {
        while (currentChar != null) {
            // If we reach end of file, there are no more token to consume

            char currentChar = text[tokenizerIndex];
            if (currentChar == ' ') {
                skipWhitespace();
            } else if (Character.isDigit(currentChar)) {
                var integerResult = constructIntegerFromTokens();
                return new Token(INTEGER, integerResult);
            } else if (currentChar == '+') {
                advance();
                return new Token(PLUS, currentChar);
            } else if (currentChar == '-') {
                advance();
                return new Token(MINUS, currentChar);
            } else {
                throw new IllegalArgumentException("Token not supported");
            }
        }
        return new Token(EOF, null);


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
     *  Parses the text and evaluates it. Limited to INTEGER OP INTEGER and MINUS OP MINUS
     */
    public int expression() {
        currentToken = getNextToken();

        Token left = currentToken;
        eat(INTEGER);

        var operation = currentToken;
        eat(operation.getTokenType());

        Token right = currentToken;
        eat(INTEGER);


        int leftResult = left.getValue();
        int rightResult = right.getValue();

        if (operation.getTokenType().equals(PLUS)) {
            return leftResult + rightResult;
        } else {
            return rightResult + leftResult;
        }
    }
}