package com.deshyan.mandela;

import static com.deshyan.mandela.TokenType.*;

public class Lexer {

    private final char[] text;
    private int tokenizerIndex;
    private Token currentToken;
    private Character currentChar;

    public Lexer(String text) {
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
    public Token getNextToken() {
        while (currentChar != null) {
            char currentChar = text[tokenizerIndex];

            switch (currentChar) {
                case ' ':
                    skipWhitespace();
                    break;
                case '*':
                    advance();
                    return new Token(MULTIPLY, currentChar);
                case '/':
                    advance();
                    return new Token(DIVIDE, currentChar);
                case '+':
                    advance();
                    return new Token(PLUS, currentChar);
                case '-':
                    advance();
                    return new Token(MINUS, currentChar);
                default:
                    if (Character.isDigit(currentChar)) {
                        var integerResult = constructIntegerFromTokens();
                        return new Token(INTEGER, integerResult);
                    } else {
                        throw new IllegalArgumentException("Token not supported");
                    }
            }
        }
        // If we reach end of file, there are no more token to consume
        return new Token(EOF, null);


    }
}
