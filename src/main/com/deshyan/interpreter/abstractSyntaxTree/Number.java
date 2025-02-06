package com.deshyan.interpreter.abstractSyntaxTree;

import lombok.Getter;

/**
 * Represents a number/INTEGER in the AST
 */
@Getter
public class Number extends AbstractSyntaxTree {
    private final int value;

    public Number(int value) {
        this.value = value;
    }
}
