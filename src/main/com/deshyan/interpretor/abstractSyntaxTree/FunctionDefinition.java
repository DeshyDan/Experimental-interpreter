package com.deshyan.interpretor.abstractSyntaxTree;

import lombok.Getter;

import java.util.List;

@Getter
public class FunctionDefinition extends AbstractSyntaxTree {
    private final String name;
    private final List<String> parameters;
    private final List<AbstractSyntaxTree> body;

    public FunctionDefinition(String name, List<String> parameters, List<AbstractSyntaxTree> body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }
}
