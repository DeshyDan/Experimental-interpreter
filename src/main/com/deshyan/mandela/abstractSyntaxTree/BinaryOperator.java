package com.deshyan.mandela.abstractSyntaxTree;

import com.deshyan.mandela.lexer.Token;
import lombok.Getter;

/**
 * BinOps are used to represent and evaluate binary operations.
 * The left operand and right operand are represented as child nodes of the BinOp node.
 * The operator is the type of operation (e.g., +, -, etc.).
 * The evaluation of the BinOp node involves applying the operator to the left and right operands.
 */
@Getter
public class BinaryOperator extends AbstractSyntaxTree {
    private final AbstractSyntaxTree left;
    private final Token operator;
    private final AbstractSyntaxTree right;

    public BinaryOperator(AbstractSyntaxTree left, Token operator, AbstractSyntaxTree right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}