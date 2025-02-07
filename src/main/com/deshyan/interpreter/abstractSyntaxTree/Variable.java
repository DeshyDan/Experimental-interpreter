package com.deshyan.interpreter.abstractSyntaxTree;

import lombok.Getter;

/**
 * Represents a variable in the AST.
 */
@Getter
public class Variable extends AbstractSyntaxTree {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }
}
