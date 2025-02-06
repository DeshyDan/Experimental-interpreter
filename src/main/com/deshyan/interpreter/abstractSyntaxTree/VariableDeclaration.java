package com.deshyan.interpreter.abstractSyntaxTree;

import lombok.Getter;

@Getter
public class VariableDeclaration extends AbstractSyntaxTree {
    private final String name;
    private final AbstractSyntaxTree value;

    public VariableDeclaration(String name, AbstractSyntaxTree value) {
        this.name = name;
        this.value = value;
    }
}