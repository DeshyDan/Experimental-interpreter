package com.deshyan.mandela.abstractSyntaxTree;

import com.deshyan.mandela.lexer.Token;
import lombok.Getter;

/**
 * Represents a number/INTEGER in the AST
 */
@Getter
public class Num extends AbstractSyntaxTree {
    private Token token;
    private int value;

    public Num(Token token) {
        this.token = token;
        this.value = token.getValue();
    }
}
