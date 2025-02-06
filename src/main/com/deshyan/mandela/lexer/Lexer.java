package com.deshyan.mandela.lexer;

import java.util.Map;

import static com.deshyan.mandela.lexer.TokenType.*;

/**
 * Responsible for breaking a sentence apart into tokens. This is also referred as lexical analysis/tokenization.
 */
public class Lexer {

    private final char[] text;
    private int tokenizerIndex;
    private Character currentChar;
    private int peekPosition;
    private final Map<String, Token> RESERVED_KEYWORDS = Map.of(
            "BEGIN", new Token(BEGIN, "BEGIN"),
            "END", new Token(END, "END")
    );

    public Lexer(String text) {
        this.text = text.toCharArray();
        this.tokenizerIndex = 0;
        this.peekPosition = 0;
        setCurrentChar(tokenizerIndex);
    }


    public Character peek() {
        var currentPeekPosition = this.peekPosition + 1;

        if (currentPeekPosition > text.length - 1) {
            return null;
        } else {
            return text[currentPeekPosition];
        }
    }

    /**
     * Handles indetifiers and reserved keywords
     */
    public Token getIdentifier() {
        StringBuilder result = new StringBuilder();
        while (currentChar != null && Character.isLetterOrDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return RESERVED_KEYWORDS.get(result.toString());
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
     * @return an integer consumed from input
     */
    private int integer() {
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

            if (currentChar == ' ') {
                skipWhitespace();
            }
            if (Character.isAlphabetic(currentChar)) {
                return getIdentifier();
            }
            if (Character.isDigit(currentChar)) {
                return new Token(INTEGER, integer());
            }

            if (currentChar == ':' && peek() == '=') {
                advance();
                advance();
                return new Token(ASSIGN, ":=");
            }

            if (currentChar == '*') {
                advance();
                return new Token(MULTIPLY, currentChar);
            }

            if (currentChar == '/') {
                advance();
                return new Token(DIVIDE, currentChar);
            }

            if (currentChar == '+') {
                advance();
                return new Token(PLUS, currentChar);
            }

            if (currentChar == '-') {
                advance();
                return new Token(MINUS, currentChar);
            }

            if (currentChar == '(') {
                advance();
                return new Token(LPAREN, currentChar);
            }

            if (currentChar == ')') {
                advance();
                return new Token(RPAREN, currentChar);
            }

            if (Character.isDigit(currentChar)) {
                var integerResult = constructIntegerFromTokens();
                return new Token(INTEGER, integerResult);
            }

            if (currentChar == ';') {
                advance();
                return new Token(SEMI, currentChar);
            }
            if (currentChar == '.') {
                advance();
                return new Token(DOT, ".");
            }
        }
        // If we reach end of file, there are no more token to consume
        return new Token(EOF, null);


    }
}
