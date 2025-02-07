package com.deshyan.interpreter.abstractSyntaxTree;

import lombok.Getter;

import java.util.List;

/**
 * Represents a function call in the AST.
 */
@Getter
public class FunctionCall extends AbstractSyntaxTree {
    private final String name;
    private final List<AbstractSyntaxTree> arguments;

    public FunctionCall(String name, List<AbstractSyntaxTree> arguments) {
        this.name = name;
        this.arguments = arguments;
    }
}
