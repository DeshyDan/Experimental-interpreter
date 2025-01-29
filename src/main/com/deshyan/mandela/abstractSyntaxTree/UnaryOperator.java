package com.deshyan.mandela.abstractSyntaxTree;

import com.deshyan.mandela.lexer.Token;
import lombok.Getter;

@Getter
public class UnaryOperator extends AbstractSyntaxTree {
    private Token token;
    private AbstractSyntaxTree expr;

    public UnaryOperator(Token token, AbstractSyntaxTree expr) {
        this.token = token;
        this.expr = expr;
    }
}