package com.deshyan.mandela;

import lombok.Getter;

@Getter
public class Token {
    private final TokenType tokenType;
    private final Object value;

    public Token(TokenType tokenType, Object value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public <T> T getValue() {
        return (T) value;
    }
    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", value='" + value + '\'' +
                '}';
    }
}
