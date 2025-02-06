package com.deshyan.mandela.abstractSyntaxTree;

import com.deshyan.mandela.lexer.Token;
import lombok.Getter;

@Getter
public class Variable extends AbstractSyntaxTree {
    private Token token;
    private String value;

    public Variable(Token token) {
        this.token = token;
        this.value = token.getValue();
    }
}
