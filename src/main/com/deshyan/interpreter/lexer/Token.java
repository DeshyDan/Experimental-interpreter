package com.deshyan.interpreter.lexer;

import lombok.Getter;

/**
 * Represents a token in the lexer.
 */
@Getter
public class Token {

    private final TokenType type;
    private final String text;

    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("Token(%s, %s)", type, text);
    }
}