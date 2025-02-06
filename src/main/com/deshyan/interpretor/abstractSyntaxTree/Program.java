package com.deshyan.interpretor.abstractSyntaxTree;

import lombok.Getter;

import java.util.List;

@Getter
public class Program extends AbstractSyntaxTree {
    private final List<AbstractSyntaxTree> statements;

    public Program(List<AbstractSyntaxTree> statements) {
        this.statements = statements;
    }
}



