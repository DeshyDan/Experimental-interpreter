package com.deshyan.interpretor.abstractSyntaxTree;

import lombok.Getter;

import java.util.List;

@Getter
public class FunctionCall extends AbstractSyntaxTree {
    private final String name;
    private final List<AbstractSyntaxTree> arguments;

    public FunctionCall(String name, List<AbstractSyntaxTree> arguments) {
        this.name = name;
        this.arguments = arguments;
    }
}
