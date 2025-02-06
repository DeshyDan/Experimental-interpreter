package com.deshyan.interpretor.abstractSyntaxTree;

import lombok.Getter;

@Getter
public class Variable extends AbstractSyntaxTree {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }
}
