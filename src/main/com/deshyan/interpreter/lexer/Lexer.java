package com.deshyan.interpreter.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            char ch = input.charAt(pos);
            if (Character.isWhitespace(ch)) {
                pos++;
            } else if (Character.isLetter(ch)) {
                tokens.add(readIdentifierOrKeyword());
            } else if (Character.isDigit(ch)) {
                tokens.add(readNumber());
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                tokens.add(new Token(TokenType.OPERATOR, String.valueOf(ch)));
                pos++;
            } else if (ch == '=' || ch == '{' || ch == '}' || ch == '(' || ch == ')' || ch == ',' || ch == ';') {
                tokens.add(new Token(TokenType.PUNCTUATION, String.valueOf(ch)));
                pos++;
            } else {
                throw new RuntimeException("Unexpected character: " + ch);
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private Token readIdentifierOrKeyword() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos++));
        }
        String text = sb.toString();
        if (text.equals("func") || text.equals("let") || text.equals("if") || text.equals("while")) {
            return new Token(TokenType.KEYWORD, text);
        }
        return new Token(TokenType.IDENTIFIER, text);
    }

    private Token readNumber() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos++));
        }
        return new Token(TokenType.NUMBER, sb.toString());
    }
}