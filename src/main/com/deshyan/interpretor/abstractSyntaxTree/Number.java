package com.deshyan.interpretor.abstractSyntaxTree;

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
