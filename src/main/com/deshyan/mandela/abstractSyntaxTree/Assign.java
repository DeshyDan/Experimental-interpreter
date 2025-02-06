package com.deshyan.mandela.abstractSyntaxTree;

public class Assign {
    private Variable left;
    private AbstractSyntaxTree right;

    public Assign(Variable left, AbstractSyntaxTree right) {
        this.left = left;
        this.right = right;
    }
}
