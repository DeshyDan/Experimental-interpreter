package com.deshyan.interpreter.abstractSyntaxTree;

public class Return extends AbstractSyntaxTree {
    public final AbstractSyntaxTree expression;

    public Return(AbstractSyntaxTree expression) {
        this.expression = expression;
    }
}