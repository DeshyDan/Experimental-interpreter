package com.deshyan.interpreter.abstractSyntaxTree;

import lombok.Getter;

/**
 * Represents a return statement in the AST.
 */
@Getter
public class Return extends AbstractSyntaxTree {
    private final AbstractSyntaxTree expression;

    public Return(AbstractSyntaxTree expression) {
        this.expression = expression;
    }
}