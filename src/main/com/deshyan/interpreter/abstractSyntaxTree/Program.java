package com.deshyan.interpreter.abstractSyntaxTree;

import lombok.Getter;

import java.util.List;

/**
 * Represents a program in the AST.
 */
@Getter
public class Program extends AbstractSyntaxTree {
    private final List<AbstractSyntaxTree> statements;

    public Program(List<AbstractSyntaxTree> statements) {
        this.statements = statements;
    }
}



