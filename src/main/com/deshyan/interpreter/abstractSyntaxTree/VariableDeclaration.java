package com.deshyan.interpreter.abstractSyntaxTree;

import lombok.Getter;

/**
 * Represents a variable declaration in the AST.
 */
@Getter
public class VariableDeclaration extends AbstractSyntaxTree {
    private final String name;
    private final AbstractSyntaxTree value;

    public VariableDeclaration(String name, AbstractSyntaxTree value) {
        this.name = name;
        this.value = value;
    }
}